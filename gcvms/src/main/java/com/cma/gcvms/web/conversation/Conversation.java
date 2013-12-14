package com.cma.gcvms.web.conversation;

import static com.cma.gcvms.web.conversation.ConversationUtil.cidParamValue;
import static com.google.common.collect.Sets.newHashSet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cma.gcvms.domain.Identifiable;

public class Conversation implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String CONVERSATION_COUNTER_KEY = "convCounter";
    private static final Logger log = LoggerFactory.getLogger(Conversation.class);

    private String id;

    /**
     *  Stores <code>conversation</code> scope beans.
     */
    private Map<String, Object> beans = new HashMap<String, Object>();

    /**
     * Each time a {@link ConversationContext} is created we assign it an id. 
     */
    private int conversationContextIdCounter = 0;
    private Stack<ConversationContext<?>> contextes = new Stack<ConversationContext<?>>();
    private int popContextOnNextPauseCounter = 0;
    private ConversationContext<?> nextContext;

    /**
     * Create a new conversation and assign it a unique id in the scope of the user's session.
     */
    public static Conversation newConversation(HttpSession session) {
        AtomicInteger counter = (AtomicInteger) session.getAttribute(CONVERSATION_COUNTER_KEY);
        if (counter == null) {
            counter = new AtomicInteger(0);
            session.setAttribute(CONVERSATION_COUNTER_KEY, counter);
        }

        return new Conversation(String.valueOf(counter.incrementAndGet()));
    }

    /**
     * Create a new conversation and assign it a unique id in the scope of the user's session.
     */
    public static Conversation newConversation(HttpSession session, ConversationContext<?> conversationContext) {
        Conversation newInstance = newConversation(session);
        newInstance.setNextContext(conversationContext);
        return newInstance;
    }

    public Conversation() {
    }

    public Conversation(String id) {
        this.id = id;
    }

    /**
     * Returns this conversation Id.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns this conversation <code>_cid</code> param value.
     */
    public String getCid() {
        return cidParamValue(id, nextContext().getId());
    }

    /**
     * Add a bean in the <code>conversation</code> scope store.
     */
    public void addBean(String name, Object bean) {
        beans.put(name, bean);
    }

    /**
     * Get a bean from the <code>conversation</code> scope store.
     */
    public Object getBean(String name) {
        return beans.get(name);
    }

    /**
     * Here to handle browser's back button
     * @param ccid
     * @param request
     */
    protected void handleOutOfSynchContext(String ccid, HttpServletRequest request) throws UnexpectedConversationException {
        ConversationContext<?> requestedContext = null;

        for (ConversationContext<?> ctx : contextes) {
            if (requestedContext == null) {
                if (ctx.getId().equals(ccid)) {
                    requestedContext = ctx;
                }
            } else {
                // we pop all contextes that are placed after the contexId requested
                incrementPopContextOnNextPauseCounter();
            }
        }

        if (requestedContext != null) {
            popContextesIfNeeded();
        } else {
            throw new UnexpectedConversationException("Uri not in sync with conversation expecting _ccid_=" + getCurrentContext().getId() + " but got " + ccid,
                    request.getRequestURI(), getUrl());
        }
    }

    /**
     * Increment the number of conversation context that must be popped from the stack by the conversation manager 
     * when it pauses the current conversation.
     * <p>
     * <i>Why don't we pop directly the context here?</i>
     * <p>
     * When an action is triggered from within a dataTable, the JSF runtime executes some EL after our action. 
     * By doing so it requests a conversation scoped bean that belongs to the current context. 
     * If this context is popped too soon, the bean is recreated!
     */
    public void incrementPopContextOnNextPauseCounter() {
        popContextOnNextPauseCounter++;
    }

    /**
     * The number of conversation contextes that must be popped on next conversation pause.
     */
    public int getPopContextOnNextPauseCounter() {
        return popContextOnNextPauseCounter;
    }

    /**
     * Set the next context. Note that this context is not pushed on the stack immediately.
     * Therefore you cannot set multiple contextes in the hope that they will stacked up.
     */
    public void setNextContext(ConversationContext<?> newContext) {
        newContext.setId(String.valueOf(++conversationContextIdCounter));
        newContext.setConversationId(getId());
        // we delay the context push because apparently some EL is invoked after bean action is performed
        // which it leads in some cases to re-creation of 'conversation scoped' bean.
        nextContext = newContext; // will be pushed at next request during resuming...
    }

    public ConversationContext<?> nextContext(ConversationContext<?> newContext) {
        setNextContext(newContext);
        return newContext;
    }

    protected void pushNextContextIfNeeded() {
        if (nextContext != null) {
            contextes.push(nextContext);
            log.debug("pushed 1 context on stack: {}", nextContext.getLabel());
            nextContext = null;
        }
    }

    /**
     * Set the <code>sub</code> var to true in the passed context and invoke {@link #setNextContext(ConversationContext)}
     * @param newContext
     * @see #setNextContext(ConversationContext)
     */
    public void setNextContextSub(ConversationContext<?> newContext) {
        setNextContext(newContext.sub());
    }

    /**
     * Set the <code>sub</code> var to true and the <code>readonly</code> var to true in the passed context and invoke {@link #setNextContext(ConversationContext)}.
     * @param newContext
     * @see #setNextContext(ConversationContext)
     */
    public void setNextContextSubReadOnly(ConversationContext<?> newContext) {
        setNextContext(newContext.sub().readonly());
    }

    /**
     * Set the <code>readonly</code> var to true in the passed context and invoke {@link #setNextContext(ConversationContext)}.
     * @param newContext
     * @see #setNextContext(ConversationContext)
     */
    public void setNextContextReadOnly(ConversationContext<?> newContext) {
        setNextContext(newContext.readonly());
    }

    public final int getConversationContextesCount() {
        return contextes.size();
    }

    /**
     * Returns the context on top on stack.
     * BEWARE, the context could have been scheduled for popping.
     */
    @SuppressWarnings("unchecked")
    public <T extends ConversationContext<?>> T getCurrentContext() {
        return (T) contextes.peek();
    }

    public Stack<ConversationContext<?>> getConversationContextes() {
        return contextes;
    }

    protected void popContextesIfNeeded() {
        if (popContextOnNextPauseCounter > 1) {
            log.debug("There are {} to pop from the stack", popContextOnNextPauseCounter);
        }

        Set<Identifiable<? extends Serializable>> dependentEntities = newHashSet();
        for (int i = 0; i < popContextOnNextPauseCounter; i++) {
            if (!contextes.isEmpty()) {
                ConversationContext<?> ccPopped = contextes.pop();
                dependentEntities.addAll(ccPopped.getDependentEntities());
                log.debug("popped 1 context from stack: {}", ccPopped.getLabel());
            } else {
                log.warn("Attention, too many pop requested! Could be source of potential bug");
            }
        }

        popContextOnNextPauseCounter = 0;

        if (contextes.isEmpty()) {
            log.info("All contextes have been popped. Natural conversation ending will be performed");
        } else {
            // adds all popped entities to current context
            getCurrentContext().addDependentEntities(dependentEntities);
        }
    }

    /**
     * Returns the view url for the next page. Used by action when returning the view.
     */
    public String nextView() {
        ConversationContext<?> context = nextContext();
        return context != null ? context.view() : "/home.faces?faces-redirect=true";
    }

    /**
     * Returns the url for the next page. Used by filter.
     */
    public String nextUrl() {
        ConversationContext<?> context = nextContext();
        return context != null ? context.getUrl() : "/home.faces";
    }

    @SuppressWarnings("unchecked")
    public <T extends ConversationContext<?>> T nextContext() {
        if (nextContext != null) {
            return (T) nextContext;
        }

        if (popContextOnNextPauseCounter > 0) {
            int nextActiveContextIndex = contextes.size() - 1 - popContextOnNextPauseCounter;
            if (nextActiveContextIndex >= 0) {
                ConversationContext<?> contextOnTopOfStackOnNextResume = contextes.elementAt(nextActiveContextIndex);
                return (T) contextOnTopOfStackOnNextResume;
            } else {
                return null;
            }
        }

        return (T) contextes.peek();
    }

    // ------------------------------------------
    // Methods below use the last pushed context
    // ------------------------------------------

    /**
     * @return the label of the last pushed context. 
     */
    public String getLabel() {
        return contextes.peek().getLabel();
    }

    /**
     * @return the menu url of the last pushed context. 
     */
    public String getUrl() {
        return contextes.peek().getUrl();
    }

    /**
     * @return the uri of the last pushed context. 
     */
    public String getViewUri() {
        return contextes.peek().getViewUri();
    }

    public void setVar(String name, Object var) {
        contextes.peek().setVar(name, var);
    }

    public Object getVar(String name) {
        return contextes.peek().getVar(name);
    }

    public <T> T getVar(String name, Class<T> expectedType) {
        return contextes.peek().getVar(name, expectedType);
    }
}
