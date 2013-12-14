package com.cma.gcvms.printer;

import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Dept;
import com.cma.gcvms.domain.Dept_;
import com.cma.gcvms.printer.support.GenericPrinter;

/**
 * {@link GenericPrinter} for {@link Dept} 
 *
 * @see GenericPrinter
 * @see TypeAwarePrinter
 */
@Named
@Singleton
public class DeptPrinter extends GenericPrinter<Dept> {
    public DeptPrinter() {
        super(Dept.class, Dept_.name);
    }

    @Override
    public String print(Dept dept, Locale locale) {
        if (dept == null) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        appendIfNotEmpty(ret, dept.getName());
        return ret.toString();
    }
}