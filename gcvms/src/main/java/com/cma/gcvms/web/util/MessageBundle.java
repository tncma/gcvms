package com.cma.gcvms.web.util;

import java.util.Enumeration;
import java.util.ResourceBundle;

import com.cma.gcvms.util.ResourcesUtil;

/**
 * This {@link ResourceBundle} is set in faces-config.xml under <code>msg</code> var.
 * <p>
 * Implementation uses Spring {@link MessageSource}.
 * <p>
 * From your JSF2 pages, you may use <code>#{msg.property_key}</code>.
 * <p>
 * _HACK_ as it is a tricky JSF/Spring integration point.
 */
public class MessageBundle extends ResourceBundle {

    private ResourcesUtil resourcesUtil;

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }

    @Override
    protected Object handleGetObject(String key) {
        if (resourcesUtil == null) {
            resourcesUtil = ResourcesUtil.getInstance();
        }
        return resourcesUtil.getProperty(key);
    }
}