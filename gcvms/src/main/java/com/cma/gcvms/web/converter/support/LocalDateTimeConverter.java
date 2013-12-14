package com.cma.gcvms.web.converter.support;

import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.inject.Singleton;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.cma.gcvms.context.LocaleHolder;

/**
 * JSF converter for JodaTime {@link LocalDateTime}.
 */
@Named
@Singleton
public class LocalDateTimeConverter implements Converter, PartialStateHolder {

    private static final String pattern = "yyyy-MM-dd HH:mm";
    private DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);

    /**
     * <code>p:calendar</code> must to use the same pattern as the converter, so we provide it here.
     */
    public String getPattern() {
        return pattern;
    }

    @Override
    public Object getAsObject(FacesContext pFacesCtx, UIComponent pComponent, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        return formatter.withLocale(LocaleHolder.getLocale()).parseDateTime(value).toLocalDateTime();
    }

    @Override
    public String getAsString(FacesContext pFacesCtx, UIComponent pComponent, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof LocalDateTime) {
            return formatter.withLocale(LocaleHolder.getLocale()).print((LocalDateTime) value);
        }

        throw new IllegalArgumentException("Expecting a LocalDateTime instance but received " + value.getClass().getName());
    }

    // PartialStateHolder implementation

    @Override
    public Object saveState(FacesContext context) {
        return "";
    }

    @Override
    public void restoreState(FacesContext context, Object state) {
    }

    private boolean transientFlag = true;

    @Override
    public boolean isTransient() {
        return transientFlag;
    }

    @Override
    public void setTransient(boolean transientFlag) {
        this.transientFlag = transientFlag;
    }

    private boolean initialState;

    @Override
    public void markInitialState() {
        initialState = true;
    }

    @Override
    public boolean initialStateMarked() {
        return initialState;
    }

    @Override
    public void clearInitialState() {
        initialState = false;
    }
}