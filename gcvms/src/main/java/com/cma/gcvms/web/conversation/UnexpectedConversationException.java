package com.cma.gcvms.web.conversation;

/**
 * Exception thrown when the end user requests an unexpected URL, that is an URL which is out of sync with the current conversation.
 */
public class UnexpectedConversationException extends Exception {
    private static final long serialVersionUID = 1L;
    private final String redirectUrl;

    public UnexpectedConversationException(String reason, String unexpectedUrl, String redirectUrl) {
        super(reason + ". requested url: " + unexpectedUrl + " => we redirect her to " + redirectUrl);
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}