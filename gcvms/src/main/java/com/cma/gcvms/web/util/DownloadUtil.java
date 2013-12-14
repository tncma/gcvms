package com.cma.gcvms.web.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public final class DownloadUtil {

    private DownloadUtil() {
    }

    /**
     * Set the http response header in order to please IE when downloading file over https.
     * see http://stackoverflow.com/questions/1918840/downloading-an-excel-file-over-https-to-ie-from-a-j2ee-application
     */
    public static void forceResponseHeaderForDownload() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.setResponseCharacterEncoding("UTF-8");
        ec.setResponseHeader("Cache-Control", "no-store");
        ec.setResponseHeader("Pragma", "private");
        ec.setResponseHeader("Expires", "1");
    }
}
