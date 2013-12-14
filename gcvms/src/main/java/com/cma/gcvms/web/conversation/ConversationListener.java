package com.cma.gcvms.web.conversation;

import javax.faces.lifecycle.Lifecycle;
import javax.servlet.http.HttpServletRequest;

/**
 * Interface to be implemented if you wish to listen to the {@link Lifecycle} of {@link Conversation conversations}.
 */
public interface ConversationListener {

    /**
     * Called after a {@link Conversation} has been created but before it starts.
     * @param conversation the conversation that was created.
     */
    void conversationCreated(Conversation conversation);

    /**
     * Called to indicate the passed conversation is being resumed.
     */
    void conversationResuming(Conversation conversation, HttpServletRequest request);

    /**
     * Called to indicate the passed Conversation is being paused.
     */
    void conversationPausing(Conversation conversation);

    /**
     * Called to indicate the conversation is ending.
     */
    void conversationEnding(Conversation conversation);
}