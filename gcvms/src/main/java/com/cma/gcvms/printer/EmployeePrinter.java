package com.cma.gcvms.printer;

import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Employee;
import com.cma.gcvms.domain.Employee_;
import com.cma.gcvms.printer.support.GenericPrinter;

/**
 * {@link GenericPrinter} for {@link Employee} 
 *
 * @see GenericPrinter
 * @see TypeAwarePrinter
 */
@Named
@Singleton
public class EmployeePrinter extends GenericPrinter<Employee> {
    public EmployeePrinter() {
        super(Employee.class, Employee_.fname, Employee_.lname, Employee_.pwd);
    }

    @Override
    public String print(Employee employee, Locale locale) {
        if (employee == null) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        appendIfNotEmpty(ret, employee.getFname());
        appendIfNotEmpty(ret, employee.getLname());
        appendIfNotEmpty(ret, employee.getPwd());
        return ret.toString();
    }
}