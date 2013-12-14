package com.cma.gcvms.web.domain.support;

import static com.cma.gcvms.web.conversation.ConversationHolder.getCurrentConversation;
import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.metamodel.PluralAttribute;

import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;

import com.cma.gcvms.domain.Identifiable;
import com.cma.gcvms.repository.support.GenericRepository;
import com.cma.gcvms.repository.support.MetamodelUtil;
import com.cma.gcvms.web.conversation.ConversationCallBack;
import com.cma.gcvms.web.permission.support.GenericPermission;
import com.cma.gcvms.web.util.MessageUtil;

/**
 * Controller that allows you to manage an entity's x-to-many association.
 */
public abstract class GenericToManyAssociation<E extends Identifiable<PK>, PK extends Serializable> {
    protected String labelKey;
    protected MessageUtil messageUtil;
    protected GenericController<E, PK> controller;
    protected GenericPermission<E> permission;
    protected GenericRepository<E, PK> repository;
    protected SelectableListDataModel<E> dataModel;
    protected final Collection<CascadeType> cascades;
    protected final boolean needEntityRemovalTracking;

    public GenericToManyAssociation(List<E> elements, GenericController<E, PK> controller, PluralAttribute<?, ?, E> attribute) {
        this.dataModel = new SelectableListDataModel<E>(elements);
        this.labelKey = buildLabelKey(attribute);
        this.controller = controller;
        this.messageUtil = controller.getMessageUtil();
        this.permission = controller.getPermission();
        this.repository = controller.getRepository();
        this.cascades = MetamodelUtil.getInstance().getCascades(attribute);
        this.needEntityRemovalTracking = !MetamodelUtil.getInstance().isOrphanRemoval(attribute);
    }

    /**
     * Return the dataModel used in the datatable component. 
     */
    public SelectableListDataModel<E> getModel() {
        return dataModel;
    }

    /**
     * Set the dataModel used in the datatable component. 
     */
    public void setModel(SelectableListDataModel<E> dataModel) {
        this.dataModel = dataModel;
    }

    /**
     * Remove the passed entity from the x-to-many association.
     */
    protected abstract void remove(E e);

    /**
     * Add the passed entity to the x-to-many association.
     */
    protected abstract void add(E e);

    /**
     * Instantiate a new entity with a view to adding it to the x-to-many assocation.
     */
    protected E create() {
        E e = repository.getNewWithDefaults();
        onCreate(e);
        return e;
    }

    /**
     * Override this method if you need to perform additional initialization such as setting
     * the entity that owns the passed x-t-many association element.
     * This method is invoked by the create method.
     * Does nothing by default.
     */
    protected void onCreate(E e) {
    }

    /**
     * Action to edit the entity corresponding to the selected row.
     * @return the implicit jsf view.
     */
    public String edit() {
        return controller.editView(labelKey, dataModel.getSelectedRow(), editCallBack, isSubView());
    }

    protected ConversationCallBack<E> editCallBack = new ConversationCallBack<E>() {
        private static final long serialVersionUID = 1L;

        @Override
        protected void onSaved(E e) {
            E previous = dataModel.getSelectedRow();
            // 'previous' is not necessarily the same instance as 'e', as 'e' may come form a merge... 
            // so we replace the old instance with the new one.
            remove(previous);
            add(e);
        }

        @Override
        protected void onOk(E e) {
            E previous = dataModel.getSelectedRow();
            // 'previous' is not necessarily the same instance as 'e', as 'e' may come form a merge... 
            // so we replace the old instance with the new one.
            remove(previous);
            add(e);
            messageUtil.infoEntity("status_edited_ok", e);
        }
    };

    /**
     * Action to view the entity corresponding to the selected row.
     * @return the implicit jsf view.
     */
    public String view() {
        return controller.editReadOnlyView(labelKey, dataModel.getSelectedRow(), isSubView());
    }

    /**
     * This datatable row selection listener invokes the {@link #edit()} or {@link #view()} action depending on the context
     * and force the navigation to the returned implicit view.
     * Use it from a p:ajax event="rowSelect".
     */
    public void onRowSelect(SelectEvent event) {
        if (getCurrentConversation().getCurrentContext().isReadOnly()) {
            Faces.navigate(view());
        } else if (permission.canEdit(dataModel.getSelectedRow())) {
            Faces.navigate(edit());
        } else {
            Faces.navigate(view());
        }
    }

    /**
     * Remove the entity corresponding to the selected row from the x-to-many association.
     */
    public void remove() {
        if (!permission.canDelete(dataModel.getSelectedRow())) {
            messageUtil.error("error_action_denied");
            return;
        }

        remove(dataModel.getSelectedRow());
        if (needEntityRemovalTracking) {
            getCurrentConversation().getCurrentContext().addDependentEntity(dataModel.getSelectedRow());
        }
        messageUtil.infoEntity("status_removed_ok", dataModel.getSelectedRow());
    }

    /**
     * Action to create a new entity. The entity is not added a priori to the x-to-many association. It is added
     * if the <code>ok</code> callback is invoked. 
     * @return the implicit jsf view.
     */
    public String add() {
        return controller.createView(labelKey, create(), addCallBack, isSubView());
    }

    protected ConversationCallBack<E> addCallBack = new ConversationCallBack<E>() {
        private static final long serialVersionUID = 1L;

        @Override
        protected void onSaved(E e) {
            add(e);
        }

        @Override
        protected void onOk(E e) {
            add(e);
            messageUtil.infoEntity("status_added_new_ok", e);
        }
    };

    public String search() {
        return select();
    }

    public String select() {
        return controller.multiSelectView(labelKey, selectCallBack, isSubView());
    }

    protected ConversationCallBack<E> selectCallBack = new ConversationCallBack<E>() {
        private static final long serialVersionUID = 1L;

        @Override
        protected void onSelected(E e) {
            if (!permission.canSelect(e)) {
                messageUtil.error("error_action_denied");
                return;
            }

            remove(e); // in case it was already selected.
            add(e);
            messageUtil.infoEntity("status_added_existing_ok", e);
        }
    };

    /**
     * @return <code>true</code> if view is related to a parent and should not be performed any persistance. <code>false</code> otherwise.
     */
    private boolean isSubView() {
        return cascades.contains(CascadeType.ALL) || cascades.contains(CascadeType.PERSIST);
    }

    private String buildLabelKey(PluralAttribute<?, ?, E> attribute) {
        return UPPER_CAMEL.to(LOWER_CAMEL, attribute.getDeclaringType().getJavaType().getSimpleName()) + "_" + attribute.getName();
    }
}
