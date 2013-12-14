package com.cma.gcvms.web.conversation;

import static org.apache.commons.lang.StringUtils.substringAfter;
import static org.apache.commons.lang.StringUtils.substringBefore;

import javax.servlet.http.HttpServletRequest;

public final class ConversationUtil {
    public static final String CID_PARAM_NAME = "_cid";
    public static final String CID_PARAM_SEPARATOR = "c";

    private ConversationUtil() {
    }

    public static String cidParamValue(String conversationId, String conversationContextId) {
        return conversationId + CID_PARAM_SEPARATOR + conversationContextId;
    }

    public static String cidParamNameValue(String conversationId, String conversationContextId) {
        return CID_PARAM_NAME + "=" + cidParamValue(conversationId, conversationContextId);
    }

    public static String getConversationId(HttpServletRequest request) {
        String _cid = request.getParameter(CID_PARAM_NAME);
        return _cid != null ? substringBefore(_cid, CID_PARAM_SEPARATOR) : null;
    }

    public static String getConversationContextId(HttpServletRequest request) {
        String _cid = request.getParameter(CID_PARAM_NAME);
        return _cid != null ? substringAfter(_cid, CID_PARAM_SEPARATOR) : null;
    }
}