package com.cma.gcvms.web.util;

import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

/**
 * Use this bean to execute JavaScript on client side.
 */
public final class PrimeFacesUtil {

    private PrimeFacesUtil() {
    }

    /**
     * Tells the client to update the search results region with the passed text.
     */
    public static void updateSearchResultsRegion(String text, int nbResults) {
        if (RequestContext.getCurrentInstance() != null) {
            RequestContext.getCurrentInstance().execute("APP.updateSearchResultsRegion(\"" + text + "\"," + nbResults + ")");
        }
    }

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public static void forceClose() {
        RequestContext.getCurrentInstance().execute("APP.menu.forceClose()");
    }
}
