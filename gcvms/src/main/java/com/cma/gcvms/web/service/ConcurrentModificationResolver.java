package com.cma.gcvms.web.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.web.conversation.ConversationManager;

/**
 * Simple service used from the concurrentModificationResolution.xhtml view.
 */
@Named
@Singleton
public class ConcurrentModificationResolver {

    @Inject
    private ConversationManager conversationManager;

    public String refresh() {
        return conversationManager.getCurrentConversation().getCurrentContext().getCallBack().ok(null);
    }

    public String discard() {
        conversationManager.endCurrentConversation();
        return "/home.faces?faces-redirect=true";
    }
}