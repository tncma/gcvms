package com.cma.gcvms.web.conversation;

import static com.cma.gcvms.web.conversation.ConversationHolder.getCurrentConversation;

import java.io.Serializable;


public class ConversationCallBack<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public ConversationCallBack() {
    }

    public final String ok(T entity) {
        incrementPopContextOnNextPauseCounter();
        onOk(entity);
        return nextView();
    }

    /**
     * The given entity has been oked. Example: it could mean
     * that it is a newly created entity, that it was validated (but not saved) and
     * that it is up to you to decide what to do with it.
     */
    protected void onOk(T entity) {
    }

    public final String selected(T entity) {
        incrementPopContextOnNextPauseCounter();
        onSelected(entity);
        return nextView();
    }

    public final String selected(T... entities) {
        incrementPopContextOnNextPauseCounter();
        for (T entity : entities) {
            onSelected(entity);
        }
        return nextView();
    }

    /**
     * The given entity has been selected.
     */
    protected void onSelected(T entity) {
    }

    public final String saved(T entity) {
        incrementPopContextOnNextPauseCounter();
        onSaved(entity);
        return nextView();
    }

    /**
     * The given entity has just been saved.
     */
    protected void onSaved(T entity) {
    }

    public final String notSaved(T entity) {
        incrementPopContextOnNextPauseCounter();
        onNotSaved(entity);
        return nextView();
    }

    /**
     * The given entity has not been saved.
     */
    protected void onNotSaved(T entity) {
    }

    public final String deleted(T entity) {
        incrementPopContextOnNextPauseCounter();
        onDeleted(entity);
        return nextView();
    }

    /**
     * The given entity has just been deleted.
     */
    protected void onDeleted(T entity) {
    }

    public final String back() {
        incrementPopContextOnNextPauseCounter();
        onBack();
        return nextView();
    }

    /**
     * No real action was performed, the user just asked to go back.
     */
    protected void onBack() {
    }

    // Context utils

    private void incrementPopContextOnNextPauseCounter() {
        getCurrentConversation().incrementPopContextOnNextPauseCounter();
    }

    private String nextView() {
        return getCurrentConversation().nextView();
    }
}