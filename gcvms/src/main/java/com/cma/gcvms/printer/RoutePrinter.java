package com.cma.gcvms.printer;

import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Route;
import com.cma.gcvms.domain.Route_;
import com.cma.gcvms.printer.support.GenericPrinter;

/**
 * {@link GenericPrinter} for {@link Route} 
 *
 * @see GenericPrinter
 * @see TypeAwarePrinter
 */
@Named
@Singleton
public class RoutePrinter extends GenericPrinter<Route> {
    public RoutePrinter() {
        super(Route.class, Route_.name);
    }

    @Override
    public String print(Route route, Locale locale) {
        if (route == null) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        appendIfNotEmpty(ret, route.getName());
        return ret.toString();
    }
}