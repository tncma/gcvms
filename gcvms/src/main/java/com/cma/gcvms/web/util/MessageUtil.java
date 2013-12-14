package com.cma.gcvms.web.util;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_FATAL;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.context.annotation.Lazy;

import com.cma.gcvms.domain.Identifiable;
import com.cma.gcvms.printer.support.TypeAwarePrinter;
import com.cma.gcvms.util.ResourcesUtil;

/**
 * Convenient bean to create JSF info/warn/error messages.

 * Business exceptions can be mapped to user friendly messages inside the {@link #error(Throwable)} method. 
 */
@Named
@Singleton
@Lazy(false)
public class MessageUtil {
    private static MessageUtil instance;

    public static MessageUtil getInstance() {
        return instance;
    }

    public static String toCssFriendly(Severity severity) {
        if (severity.equals(SEVERITY_INFO)) {
            return "info";
        } else if (severity.equals(SEVERITY_WARN)) {
            return "warn";
        } else if (severity.equals(SEVERITY_ERROR)) {
            return "error";
        } else if (severity.equals(SEVERITY_FATAL)) {
            return "fatal";
        }

        throw new IllegalStateException("Unexpected message severity: " + severity.toString());
    }

    @Inject
    private ResourcesUtil resourcesUtil;
    @Inject
    private TypeAwarePrinter printer;

    public MessageUtil() {
        instance = this;
    }

    // -- info

    public void info(String summaryKey, Object... args) {
        addFacesMessageUsingKey(SEVERITY_INFO, summaryKey, args);
    }

    public void infoEntity(String summaryKey, Identifiable<?> entity) {
        addFacesMessageUsingKey(SEVERITY_INFO, summaryKey, printer.print(entity));
    }

    public FacesMessage newInfo(String summaryKey, Object... args) {
        return newFacesMessageUsingKey(SEVERITY_INFO, summaryKey, args);
    }

    // -- warning

    public void warning(String summaryKey, Object... args) {
        addFacesMessageUsingKey(SEVERITY_WARN, summaryKey, args);
    }

    public FacesMessage newWarning(String summaryKey, Object... args) {
        return newFacesMessageUsingKey(SEVERITY_WARN, summaryKey, args);
    }

    // -- error

    public void error(String summaryKey, Object... args) {
        addFacesMessageUsingKey(SEVERITY_ERROR, summaryKey, args);
    }

    public FacesMessage newError(String summaryKey, Object... args) {
        return newFacesMessageUsingKey(SEVERITY_ERROR, summaryKey, args);
    }

    // -- fatal

    public void fatal(String summaryKey, Object... args) {
        addFacesMessageUsingKey(SEVERITY_FATAL, summaryKey, args);
    }

    public FacesMessage newFatal(String summaryKey, Object... args) {
        return newFacesMessageUsingKey(SEVERITY_FATAL, summaryKey, args);
    }

    private void addFacesMessage(FacesMessage fm) {
        if (fm != null) {
            FacesContext.getCurrentInstance().addMessage(null, fm);
        }
    }

    private void addFacesMessageUsingKey(Severity severity, String summaryKey, Object arg) {
        addFacesMessageUsingKey(severity, summaryKey, new Object[] { arg });
    }

    private void addFacesMessageUsingKey(Severity severity, String summaryKey, Object[] args) {
        addFacesMessage(newFacesMessageUsingKey(severity, summaryKey, args));
    }

    private FacesMessage newFacesMessageUsingKey(Severity severity, String summaryKey, Object[] args) {
        return newFacesMessageUsingText(severity, resourcesUtil.getProperty(summaryKey, args));
    }

    private FacesMessage newFacesMessageUsingText(Severity severity, String text) {
        FacesMessage fm = new FacesMessage(text);
        fm.setSeverity(severity);
        return fm;
    }
}