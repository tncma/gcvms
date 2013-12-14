package com.cma.gcvms.web.conversation;

/**
 * Holds the current {@link Conversation} in the current thread of execution.
 */
public final class ConversationHolder {
    private static final ThreadLocal<Conversation> currentConversationHolder = new ThreadLocal<Conversation>();

    private ConversationHolder() {
    }

    /**
     * Returns the {@link Conversation} that is bound to the current thread of execution.
     */
    public static Conversation getCurrentConversation() {
        return currentConversationHolder.get();
    }

    /**
     * Bind the passed {@link Conversation} to the current thread of execution.
     */
    public static void setCurrentConversation(Conversation conversation) {
        currentConversationHolder.set(conversation);
    }
}