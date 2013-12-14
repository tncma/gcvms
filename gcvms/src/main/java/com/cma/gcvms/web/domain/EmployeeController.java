package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Employee;
import com.cma.gcvms.printer.EmployeePrinter;
import com.cma.gcvms.repository.EmployeeRepository;
import com.cma.gcvms.web.domain.support.GenericController;
import com.cma.gcvms.web.permission.EmployeePermission;

/**
 * Stateless controller for {@link Employee} conversation start.
 */
@Named
@Singleton
public class EmployeeController extends GenericController<Employee, Integer> {
    public static final String EMPLOYEE_EDIT_URI = "/domain/employeeEdit.faces";
    public static final String EMPLOYEE_SELECT_URI = "/domain/employeeSelect.faces";

    @Inject
    public EmployeeController(EmployeeRepository employeeRepository, EmployeePermission employeePermission, EmployeePrinter employeePrinter) {
        super(employeeRepository, employeePermission, employeePrinter, EMPLOYEE_SELECT_URI, EMPLOYEE_EDIT_URI);
    }
}