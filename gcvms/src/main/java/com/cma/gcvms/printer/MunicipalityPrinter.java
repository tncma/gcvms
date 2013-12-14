package com.cma.gcvms.printer;

import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Municipality;
import com.cma.gcvms.domain.Municipality_;
import com.cma.gcvms.printer.support.GenericPrinter;

/**
 * {@link GenericPrinter} for {@link Municipality} 
 *
 * @see GenericPrinter
 * @see TypeAwarePrinter
 */
@Named
@Singleton
public class MunicipalityPrinter extends GenericPrinter<Municipality> {
    public MunicipalityPrinter() {
        super(Municipality.class, Municipality_.name);
    }

    @Override
    public String print(Municipality municipality, Locale locale) {
        if (municipality == null) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        appendIfNotEmpty(ret, municipality.getName());
        return ret.toString();
    }
}