package com.cma.gcvms.context;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

public final class LocaleHolder {

    private LocaleHolder() {
    }

    public static Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    public static void setLocale(Locale locale) {
        LocaleContextHolder.setLocale(locale);
    }

    public static void resetLocaleContext() {
        LocaleContextHolder.resetLocaleContext();
    }
}