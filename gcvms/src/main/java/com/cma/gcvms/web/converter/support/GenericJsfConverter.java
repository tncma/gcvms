package com.cma.gcvms.web.converter.support;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Base JSF converter to store objects in the jsf tree. 
 */
@Named
@Singleton
public class GenericJsfConverter implements Converter {
    private static final String COMPONENT_UNIQUE_PREFIX = "object:";
    private static final String COMPONENT_NULL_VALUE = COMPONENT_UNIQUE_PREFIX + "null";

    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        if (value == null || COMPONENT_NULL_VALUE.equals(value)) {
            return null;
        }
        return component.getAttributes().get(value);
    }

    public String getAsString(FacesContext context, UIComponent component, Object object) {
        if (object == null) {
            return COMPONENT_NULL_VALUE;
        }
        String unique = COMPONENT_UNIQUE_PREFIX + RandomStringUtils.randomAlphanumeric(36);
        component.getAttributes().put(unique, object);
        return unique;
    }
}