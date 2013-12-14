package com.cma.gcvms.web.converter.support;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Boolean converter, used by <code>selectManyCheckbox</code> tag.
 */
@Named
@Singleton
public class BooleanConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        if (arg2 != null && !arg2.isEmpty()) {
            return Boolean.valueOf(arg2);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        if (arg2 instanceof Boolean) {
            return arg2.toString();
        }
        return "";
    }
}