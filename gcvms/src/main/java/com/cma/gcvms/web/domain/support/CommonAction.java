package com.cma.gcvms.web.domain.support;

import static com.cma.gcvms.web.conversation.ConversationHolder.getCurrentConversation;

import com.cma.gcvms.web.conversation.ConversationContext;
import com.cma.gcvms.web.conversation.ConversationManager;

/**
 * Simple actions that can be shared.
 */
public abstract class CommonAction<E> {

    /**
     * Back action is used from readonly page to go back. It is expected to be non-ajax.
     */
    public String back() {
        return context().getCallBack().back();
    }

    /**
     * Ends the current conversation. It is expected to be non-ajax.
     */
    public String quit() {
        ConversationManager.getInstance().endCurrentConversation();
        return "/home.faces?faces-redirect=true"; // TODO: clean url, referer or else
    }

    /**
     * Returns the current {@link ConversationContext}.
     */
    public ConversationContext<E> context() {
        return getCurrentConversation().getCurrentContext();
    }
}