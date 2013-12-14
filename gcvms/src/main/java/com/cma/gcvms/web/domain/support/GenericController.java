package com.cma.gcvms.web.domain.support;

import static com.cma.gcvms.repository.support.PropertySelector.newPropertySelector;
import static com.cma.gcvms.web.conversation.ConversationHolder.getCurrentConversation;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.Serializable;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cma.gcvms.domain.Identifiable;
import com.cma.gcvms.printer.support.GenericPrinter;
import com.cma.gcvms.printer.support.TypeAwarePrinter;
import com.cma.gcvms.repository.support.GenericRepository;
import com.cma.gcvms.repository.support.JpaUniqueUtil;
import com.cma.gcvms.repository.support.SearchParameters;
import com.cma.gcvms.web.conversation.ConversationCallBack;
import com.cma.gcvms.web.conversation.ConversationContext;
import com.cma.gcvms.web.conversation.ConversationManager;
import com.cma.gcvms.web.permission.support.GenericPermission;
import com.cma.gcvms.web.util.MessageUtil;
import com.google.common.base.Splitter;

/**
 * Base controller for JPA entities providing helper methods to:
 * <ul>
 *  <li>start conversations</li>
 *  <li>create conversation context</li>
 *  <li>support autoComplete component</li>
 *  <li>perform actions</li>
 *  <li>support excel export</li>
 * </ul>
 */
public abstract class GenericController<E extends Identifiable<PK>, PK extends Serializable> {
    private static final Logger log = LoggerFactory.getLogger(GenericController.class);
    private static final String PERMISSION_DENIED = "/error/accessdenied";
    private String selectUri;
    private String editUri;

    @Inject
    protected ConversationManager conversationManager;
    @Inject
    protected JpaUniqueUtil jpaUniqueUtil;
    @Inject
    protected MessageUtil messageUtil;
    @Inject
    protected TypeAwarePrinter typeAwarePrinter;
    protected GenericRepository<E, PK> repository;
    protected GenericPermission<E> permission;
    protected GenericPrinter<E> printer;

    public GenericController(GenericRepository<E, PK> repository, GenericPermission<E> permission, GenericPrinter<E> printer, String selectUri, String editUri) {
        this.repository = checkNotNull(repository);
        this.permission = checkNotNull(permission);
        this.printer = checkNotNull(printer);
        this.selectUri = checkNotNull(selectUri);
        this.editUri = checkNotNull(editUri);
    }

    public final GenericRepository<E, PK> getRepository() {
        return repository;
    }

    public final GenericPermission<E> getPermission() {
        return permission;
    }

    public final MessageUtil getMessageUtil() {
        return messageUtil;
    }

    public String getPermissionDenied() {
        return PERMISSION_DENIED;
    }

    public String getSelectUri() {
        return selectUri;
    }

    public String getEditUri() {
        return editUri;
    }

    // ----------------------------------------
    // START CONVERSATION PROGRAMATICALY
    // ----------------------------------------

    /**
     * Start a new {@link Conversation} that allows the user to search an existing entity.
     * This method can be invoked from an h:commandLink's action attribute.
     * @return the implicit first view for the newly created conversation.
     */
    public String beginSearch() {
        if (!permission.canSearch()) {
            return getPermissionDenied();
        }
        return beginConversation(newSearchContext(getSearchLabelKey()));
    }

    /**
     * Start a new {@link Conversation} that allows the user to create a new entity.
     * This method can be invoked from an h:commandLink's action attribute.
     * @return the implicit first view for the newly created conversation.
     */
    public String beginCreate() {
        if (!permission.canCreate()) {
            return getPermissionDenied();
        }
        return beginConversation(newEditContext(getCreateLabelKey(), repository.getNewWithDefaults()));
    }

    /**
     * Start a new {@link Conversation} using the passed ctx as the first conversation context.
     * @return the implicit first view for the newly created conversation.
     */
    public String beginConversation(ConversationContext<E> ctx) {
        return conversationManager.beginConversation(ctx).nextView();
    }

    // ----------------------------------------
    // AUTO COMPLETE SUPPORT  
    // ----------------------------------------

    /**
     * Auto-complete support. This method is used by primefaces autoComplete component.
     */
    public List<E> complete(String value) {
        try {
            SearchParameters searchParameters = new SearchParameters() //
                    .limitBroadSearch() //
                    .distinct() //
                    .orMode();
            E template = repository.getNew();
            for (String property : completeProperties()) {
                searchParameters.addProperty(newPropertySelector(property, repository.getType()).selected(value));
            }
            return repository.find(template, searchParameters);
        } catch (Exception e) {
            log.warn("error during complete", e);
            throw propagate(e);
        }
    }

    protected Iterable<String> completeProperties() {
        String completeOnProperties = parameter("completeOnProperties", String.class);
        return isBlank(completeOnProperties) ? printer.getDisplayedAttributes() : Splitter.on(";,").omitEmptyStrings().split(completeOnProperties);
    }

    public List<String> completeProperty(String value) {
        return completeProperty(value, parameter("property", String.class), parameter("maxResults", Integer.class));
    }

    public List<String> completeProperty(String value, String property) {
        return completeProperty(value, property, null);
    }

    public List<String> completeProperty(String toMatch, String property, Integer maxResults) {
        List<String> values = completePropertyInDatabase(toMatch, property, maxResults);
        if (isBlank(toMatch) || values.contains(toMatch)) {
            // the term is already in the results, return them directly
            return values;
        } else {
            // add the term before the results as it is not part of the results
            List<String> retWithValue = newArrayList(toMatch);
            retWithValue.addAll(values);
            return retWithValue;
        }
    }

    protected List<String> completePropertyInDatabase(String value, String property, Integer maxResults) {
        try {
            SearchParameters searchParameters = new SearchParameters() //
                    .limitBroadSearch() //
                    .caseInsensitive() //
                    .anywhere() //
                    .distinct();
            if (maxResults != null) {
                searchParameters.setMaxResults(maxResults);
            }
            searchParameters.addProperty(newPropertySelector(property, repository.getType()).selected(value));
            return repository.findProperty(String.class, repository.getNew(), searchParameters, property);
        } catch (Exception e) {
            log.warn("error during completePropertyInDatabase", e);
            throw propagate(e);
        }
    }

    /**
     * A simple autoComplete that returns exactly the input. It is used in search forms with {@link PropertySelector}.
     */
    public List<String> completeSame(String value) {
        return newArrayList(value);
    }

    @SuppressWarnings("unchecked")
    protected <T> T parameter(String propertyName, Class<T> expectedType) {
        return (T) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get(propertyName);
    }

    protected SearchParameters defaultOrder(SearchParameters searchParameters) {
        return searchParameters;
    }

    /**
     * Decision helper used when handling ajax autoComplete event and regular page postback.
     */
    public boolean shouldReplace(E currentEntity, E selectedEntity) {
        if (currentEntity == selectedEntity) {
            return false;
        }

        if (currentEntity != null && selectedEntity != null && currentEntity.isIdSet() && selectedEntity.isIdSet()) {
            if (selectedEntity.getId().equals(currentEntity.getId())) {
                Comparable<Object> currentVersion = repository.getVersion(currentEntity);
                if (currentVersion == null) {
                    // assume no version at all is available
                    // let's stick with current entity.
                    return false;
                }
                Comparable<Object> selectedVersion = repository.getVersion(selectedEntity);
                if (currentVersion.compareTo(selectedVersion) == 0) {
                    // currentEntity could have been edited and not yet saved, we keep it.
                    return false;
                } else {
                    // we could have an optimistic locking exception at save time
                    // TODO: what should we do here?
                    return false;
                }
            }
        }
        return true;
    }

    // ----------------------------------------
    // CREATE IMPLICIT EDIT VIEW
    // ----------------------------------------

    /**
     * Helper to create a new {@link ConversationContext} to view the passed entity and set it as the current conversation's next context.  
     * The vars <code>sub</code> <code>readonly</code> are set to true.
     * The permission {@link GenericPermission#canView()} is checked.
     * 
     * @param labelKey label key for breadCrumb and conversation menu.
     * @param e the entity to view.
     * @return the implicit view to access this context.
     */
    public String editSubReadOnlyView(String labelKey, E e) {
        return editReadOnlyView(labelKey, e, true);
    }

    public String editReadOnlyView(String labelKey, E e, boolean sub) {
        if (!permission.canView(e)) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = newEditContext(labelKey, e).sub(sub).readonly();
        return getCurrentConversation().nextContext(ctx).view();
    }

    /**
     * Helper to create a new {@link ConversationContext} to edit the passed entity and set it as the current conversation's next context.  
     * The var <code>sub</code> is set to true.
     * The permission {@link GenericPermission#canEdit()} is checked.
     * 
     * @param labelKey label key for breadCrumb and conversation menu.
     * @param e the entity to edit.
     * @return the implicit view to access this context.
     */
    public String editSubView(String labelKey, E e, ConversationCallBack<E> editCallBack) {
        return editView(labelKey, e, editCallBack, true);
    }

    public String editView(String labelKey, E e, ConversationCallBack<E> editCallBack, boolean sub) {
        if (!permission.canEdit(e)) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = newEditContext(labelKey, e, editCallBack).sub(sub);
        return getCurrentConversation().nextContext(ctx).view();
    }

    /**
     * Helper to create a new {@link ConversationContext} to create a new entity and set it as the current conversation's next context.  
     * The var <code>sub</code> is set to true.
     * 
     * @param labelKey label key for breadCrumb and conversation menu.
     * @return the implicit view to access this context.
     */
    public String createSubView(String labelKey, ConversationCallBack<E> createCallBack) {
        return createView(labelKey, createCallBack, true);
    }

    /**
     * Helper to create a new {@link ConversationContext} to edit the passed new entity and set it as the current conversation's next context.  
     * The var <code>sub</code> is set to true.
     * The permission {@link GenericPermission#canCreate()} is checked.
     * 
     * @param labelKey label key for breadCrumb and conversation menu.
     * @param e the entity to edit.
     * @return the implicit view to access this context.
     */
    public String createSubView(String labelKey, E e, ConversationCallBack<E> createCallBack) {
        return createView(labelKey, e, createCallBack, true);
    }

    public String createView(String labelKey, ConversationCallBack<E> createCallBack, boolean sub) {
        return createView(labelKey, repository.getNewWithDefaults(), createCallBack, sub);
    }

    public String createView(String labelKey, E e, ConversationCallBack<E> createCallBack, boolean sub) {
        if (!permission.canCreate()) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = newEditContext(labelKey, e, createCallBack).sub(sub);
        return getCurrentConversation().nextContext(ctx).view();
    }

    // ----------------------------------------
    // CREATE IMPLICIT SELECT VIEW
    // ----------------------------------------

    public String selectSubView(String labelKey, ConversationCallBack<E> selectCallBack) {
        return selectView(labelKey, selectCallBack, true);
    }

    public String selectView(String labelKey, ConversationCallBack<E> selectCallBack, boolean sub) {
        if (!permission.canSelect()) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = newSearchContext(labelKey, selectCallBack).sub(sub);
        return getCurrentConversation().nextContext(ctx).view();
    }

    public String multiSelectSubView(String labelKey, ConversationCallBack<E> selectCallBack) {
        return multiSelectView(labelKey, selectCallBack, true);
    }

    public String multiSelectView(String labelKey, ConversationCallBack<E> selectCallBack, boolean sub) {
        if (!permission.canSelect()) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = newSearchContext(labelKey, selectCallBack).sub(sub);
        ctx.setVar("multiCheckboxSelection", true);
        return getCurrentConversation().nextContext(ctx).view();
    }

    // ----------------------------------------
    // CREATE EDIT CONVERSATION CONTEXT
    // ----------------------------------------

    /**
     * Helper to construct a new {@link ConversationContext} to edit an entity.
     * @param e the entity to edit.
     */
    public ConversationContext<E> newEditContext(E e) {
        return new ConversationContext<E>().entity(e).isNewEntity(!e.isIdSet()).viewUri(getEditUri());
    }

    public ConversationContext<E> newEditContext(String labelKey, E e) {
        return newEditContext(e).labelKey(labelKey);
    }

    public ConversationContext<E> newEditContext(String labelKey, E e, ConversationCallBack<E> callBack) {
        return newEditContext(labelKey, e).callBack(callBack);
    }

    // ----------------------------------------
    // CREATE SEARCH CONVERSATION CONTEXT
    // ----------------------------------------

    /**
     * Helper to construct a new {@link ConversationContext} for search/selection.
     */
    public ConversationContext<E> newSearchContext() {
        return new ConversationContext<E>(getSelectUri());
    }

    public ConversationContext<E> newSearchContext(String labelKey) {
        return newSearchContext().labelKey(labelKey);
    }

    public ConversationContext<E> newSearchContext(String labelKey, ConversationCallBack<E> callBack) {
        return newSearchContext(labelKey).callBack(callBack);
    }

    // ----------------------------------------
    // ACTIONS INVOKED FORM THE VIEW
    // ----------------------------------------

    public ConversationContext<E> getSelectedContext(E selected) {
        return newEditContext(getEditUri(), selected);
    }

    /**
     * Action to create a new entity.
     */
    public String create() {
        if (!permission.canCreate()) {
            return getPermissionDenied();
        }
        E newEntity = repository.getNewWithDefaults();
        ConversationContext<E> ctx = getSelectedContext(newEntity).labelKey(getCreateLabelKey());
        return getCurrentConversation().nextContext(ctx).view();
    }

    /**
     * Support for {@link GenericLazyDataModel.#edit()} and {@link GenericLazyDataModel#onRowSelect(org.primefaces.event.SelectEvent)} methods 
     */
    public String edit(E entity) {
        if (!permission.canEdit(entity)) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = getSelectedContext(entity).labelKey(getEditLabelKey(), typeAwarePrinter.print(entity));
        return getCurrentConversation().nextContext(ctx).view();
    }

    /**
     * Support for the {@link GenericLazyDataModel.#view()} method
     */
    public String view(E entity) {
        if (!permission.canView(entity)) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = getSelectedContext(entity).sub().readonly().labelKey(getViewLabelKey(), typeAwarePrinter.print(entity));
        return getCurrentConversation().nextContext(ctx).view();
    }

    /**
     * Return the print friendly view for the passed entity. I can be invoked directly from the view.
     */
    public String print(E entity) {
        if (!permission.canView(entity)) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = getSelectedContext(entity).readonly().print().labelKey(getViewLabelKey(), typeAwarePrinter.print(entity));
        return getCurrentConversation().nextContext(ctx).view();
    }

    protected String select(E entity) {
        if (!permission.canSelect()) {
            return getPermissionDenied();
        }
        return getCurrentConversation() //
                .<ConversationContext<E>> getCurrentContext() //
                .getCallBack() //
                .selected(entity);
    }

    protected String getSearchLabelKey() {
        return getLabelName() + "_search";
    }

    protected String getCreateLabelKey() {
        return getLabelName() + "_create";
    }

    protected String getEditLabelKey() {
        return getLabelName() + "_edit";
    }

    protected String getViewLabelKey() {
        return getLabelName() + "_view";
    }

    protected String getLabelName() {
        return WordUtils.uncapitalize(getEntityName());
    }

    private String getEntityName() {
        return repository.getType().getSimpleName();
    }
}
