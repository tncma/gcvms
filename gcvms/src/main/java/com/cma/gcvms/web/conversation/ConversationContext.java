package com.cma.gcvms.web.conversation;

import static com.cma.gcvms.web.conversation.ConversationUtil.cidParamNameValue;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.unmodifiableSet;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.cma.gcvms.domain.Identifiable;
import com.cma.gcvms.util.ResourcesUtil;

public class ConversationContext<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *  Stores 'conversationContext' scope beans.
     */
    private Map<String, Object> beans = newHashMap();

    /**
     *  Stores variables such as <code>readonly</code>, <code>sub</code>, etc.
     */
    private Map<String, Object> vars = newHashMap();
    private String id; // context id
    private String conversationId;
    private ConversationCallBack<T> callBack = new ConversationCallBack<T>();
    private String labelKey = null;
    private Object[] labelKeyArgs = null;
    private String viewUri;
    private boolean isNewEntity;

    public ConversationContext() {
    }

    public ConversationContext(String viewUri) {
        this.viewUri = viewUri;
    }

    /**
     * Set this conversation context id.
     */
    protected void setId(String id) {
        this.id = id;
    }

    public ConversationContext<T> id(String id) {
        setId(id);
        return this;
    }

    public String getId() {
        return id;
    }

    protected void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public ConversationContext<T> conversationId(String conversationId) {
        setConversationId(conversationId);
        return this;
    }

    /**
     * Set the entity that will be used by the XxxForm.
     */
    public void setEntity(T entity) {
        setVar("_entity", entity);
    }

    public ConversationContext<T> entity(T entity) {
        setEntity(entity);
        return this;
    }

    /**
     * Returns the original entity that was passed to this context.
     */
    @SuppressWarnings("unchecked")
    public T getEntity() {
        return (T) vars.get("_entity");
    }

    public void setIsNewEntity(boolean isNewEntity) {
        this.isNewEntity = isNewEntity;
    }

    public ConversationContext<T> isNewEntity(boolean isNewEntity) {
        setIsNewEntity(isNewEntity);
        return this;
    }

    public ConversationContext<T> newEntity() {
        return isNewEntity(true);
    }

    public boolean isNewEntity() {
        return isNewEntity;
    }

    /**
     * Use this method if you want the entity to be loaded afterward.
     */
    public void setEntityId(Serializable entityId) {
        setVar("entityId", entityId);
    }

    public ConversationContext<T> entityId(Serializable entityId) {
        setEntityId(entityId);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <PK> PK getEntityIdAndRemove() {
        return (PK) vars.remove("entityId");
    }

    /**
     * Sets the label displayed in the conversation menu.
     * @param labelKey the resource property key.
     * @param labelKeyArgs the optional args
     */
    public void setLabelKey(String labelKey, Object... labelKeyArgs) {
        this.labelKey = labelKey;
        this.labelKeyArgs = labelKeyArgs;
    }

    public ConversationContext<T> labelKey(String labelKey, Object... labelKeyArgs) {
        setLabelKey(labelKey, labelKeyArgs);
        return this;
    }

    /**
     * Resolve the label at runtime as it depends on the current language.
     */
    public String getLabel() {
        return ResourcesUtil.getInstance().getProperty(labelKey, labelKeyArgs);
    }

    /**
     * Sets the viewUri attached to this context. 
     */
    public void setViewUri(String viewUri) {
        this.viewUri = viewUri;
    }

    public ConversationContext<T> viewUri(String viewUri) {
        setViewUri(viewUri);
        return this;
    }

    /**
     * The viewUri attached to this context.
     */
    public String getViewUri() {
        return viewUri;
    }

    /**
     * Sets the <code>sub</code> variable. This variable is used in the xhtml view to render certain menus/buttons.
     */
    public void setSub(boolean sub) {
        setVar("sub", sub);
    }

    public ConversationContext<T> sub(boolean sub) {
        setSub(sub);
        return this;
    }

    public ConversationContext<T> sub() {
        return sub(true);
    }

    public boolean isSub() {
        return getVar("sub", Boolean.class) != null ? getVar("sub", Boolean.class) : false;
    }

    /**
     * Sets the <code>readonly</code> variable.
     */
    public void setReadonly(boolean readonly) {
        setVar("readonly", readonly);
    }

    public ConversationContext<T> readonly(boolean readonly) {
        setReadonly(readonly);
        return this;
    }

    public ConversationContext<T> readonly() {
        return readonly(true);
    }

    public boolean isReadOnly() {
        return getVar("readonly", Boolean.class) != null ? getVar("readonly", Boolean.class) : false;
    }

    /**
     * Sets the <code>print</code> variable. It activates a printer friendly mode.
     */
    public void setPrint(boolean print) {
        setVar("print", print);
    }

    public ConversationContext<T> print(boolean print) {
        setPrint(print);
        return this;
    }

    public ConversationContext<T> print() {
        return print(true);
    }

    public boolean isPrint() {
        return getVar("print", Boolean.class) != null ? getVar("print", Boolean.class) : false;
    }

    /**
     * Sets the <code>dependentEntities</code> variables. It keeps track of entities to be saved/merged just as the primary <code>entity</code>.
     */
    public ConversationContext<T> dependentEntity(Identifiable<? extends Serializable>... dependents) {
        addDependentEntities(newArrayList(dependents));
        return this;
    }

    public void addDependentEntity(Identifiable<? extends Serializable> dependent) {
        Set<Identifiable<? extends Serializable>> dependents = getDependents();
        dependents.add(dependent);

        setVar("dependent", dependents);
    }

    public void addDependentEntities(Collection<? extends Identifiable<? extends Serializable>> dependentsCollection) {
        Set<Identifiable<? extends Serializable>> dependents = getDependents();
        dependents.addAll(dependentsCollection);

        setVar("dependent", dependents);
    }

    public Set<Identifiable<? extends Serializable>> getDependentEntities() {
        Set<Identifiable<? extends Serializable>> dependents = getDependents();
        return unmodifiableSet(dependents);
    }

    public void clearDependentEntities() {
        setVar("dependent", newHashSet());
    }

    /**
     * @return dependents entities set if exists, empty set otherwise.
     */
    @SuppressWarnings("unchecked")
    private Set<Identifiable<? extends Serializable>> getDependents() {
        Set<Identifiable<? extends Serializable>> dependents = (Set<Identifiable<? extends Serializable>>) getVar("dependent", Set.class);
        if (dependents == null) {
            dependents = newHashSet();
        }
        return dependents;
    }

    /**
     * The callback to use just after this context is flagged for pop from the conversation's context stack. 
     */
    public void setCallBack(ConversationCallBack<T> callBack) {
        this.callBack = callBack;
    }

    public ConversationContext<T> callBack(ConversationCallBack<T> callBack) {
        setCallBack(callBack);
        return this;
    }

    public ConversationCallBack<T> getCallBack() {
        return callBack;
    }

    /**
     * Returns the URL associated to this context. It is used for direct access, from the conversation menu or the filter.
     */
    public String getUrl() {
        checkViewUriAndConversationId();
        return viewUri + "?" + cidParamNameValue(conversationId, getId());
    }

    /**
     * Return the view to display for this context. It is used by actions when returning a view.
     */
    public String view() {
        checkViewUriAndConversationId();
        return viewUri + "?faces-redirect=true&" + cidParamNameValue(conversationId, getId());
    }

    private void checkViewUriAndConversationId() {
        if (viewUri == null) {
            throw new IllegalStateException("Developer! viewUri is null, it must be set before calling view() or getUrl() methods");
        }

        if (conversationId == null) {
            throw new IllegalStateException("Developer! conversationId is null, it must be set before calling view() or getUrl() methods");
        }
    }

    // ----------------------------------
    // Support for conversation scope
    // ----------------------------------

    /**
     * Add a conversation scoped bean spring bean to this context. A bean is added either 'automatically' or manually.
     * In the latter case, it is autowired afterward. (see {@link ConversationScope}).
     */
    public void addBean(String name, Object bean) {
        beans.put(name, bean);
    }

    @SuppressWarnings("unchecked")
    public <E> E getBean(String name, Class<E> expectedType) {
        return (E) beans.get(name);
    }

    public void setVar(String name, Object var) {
        vars.put(name, var);
    }

    public Object getVar(String name) {
        return vars.get(name);
    }

    @SuppressWarnings("unchecked")
    public <E> E getVar(String name, Class<E> expectedType) {
        return (E) vars.get(name);
    }

    // ---------------------------
    // For debug/training purposes 
    // ---------------------------

    public List<Entry<String, Object>> getBeanEntries() {
        return newArrayList(beans.entrySet());
    }

    public List<Entry<String, Object>> getVarEntries() {
        return newArrayList(vars.entrySet());
    }
}