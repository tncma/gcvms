package com.cma.gcvms.printer.support;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.List;
import java.util.Locale;

import javax.persistence.metamodel.Attribute;

import com.cma.gcvms.context.LocaleHolder;
import com.cma.gcvms.repository.support.JpaUtil;

public abstract class GenericPrinter<T> {
    private final Class<T> clazz;
    private final List<String> displayedAttributes;

    public GenericPrinter(Class<T> clazz, Attribute<?, ?>... displayedAttributes) {
        this.clazz = clazz;
        this.displayedAttributes = newArrayList(JpaUtil.getInstance().toNames(displayedAttributes));
    }

    public Class<T> getTarget() {
        return clazz;
    }

    public String print(T document) {
        return print(document, LocaleHolder.getLocale());
    }

    public abstract String print(T object, Locale locale);

    public List<String> getDisplayedAttributes() {
        return displayedAttributes;
    }

    protected void appendIfNotEmpty(StringBuilder builder, String value) {
        if (isNotBlank(value)) {
            if (builder.length() != 0) {
                builder.append('/');
            }
            builder.append(value.trim());
        }
    }

    protected void appendIfNotEmpty(StringBuilder builder, Object value) {
        if (value != null) {
            if (builder.length() != 0) {
                builder.append('/');
            }
            builder.append(value);
        }
    }
}