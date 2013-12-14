package com.cma.gcvms.printer.support;

import static com.google.common.collect.Maps.newHashMap;
import static org.hibernate.proxy.HibernateProxyHelper.getClassWithoutInitializingProxy;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.context.LocaleHolder;

/**
 * Given the type of the object use the corresponding {@link GenericPrinter}, or use {@link #toString()} method.
 */
@Named("printer")
@Singleton
public class TypeAwarePrinter {
    private Map<Class<?>, GenericPrinter<?>> printers = newHashMap();

    @Inject
    void buildCache(List<GenericPrinter<?>> registredPrinters) {
        for (GenericPrinter<?> printer : registredPrinters) {
            printers.put(printer.getTarget(), printer);
        }
    }

    public String print(Object object) {
        return print(object, LocaleHolder.getLocale());
    }

    @SuppressWarnings("unchecked")
    public String print(Object object, Locale locale) {
        if (object == null) {
            return "";
        }

        // note: getClassWithoutInitializingProxy expects a non null object
        // _HACK_ as we depend on hibernate here.
        @SuppressWarnings("rawtypes")
        GenericPrinter printer = printers.get(getClassWithoutInitializingProxy(object));
        return printer == null ? object.toString() : printer.print(object, locale);
    }
}