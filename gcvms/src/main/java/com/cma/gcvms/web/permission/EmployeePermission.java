package com.cma.gcvms.web.permission;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Employee;
import com.cma.gcvms.web.permission.support.GenericPermission;

@Named
@Singleton
public class EmployeePermission extends GenericPermission<Employee> {
    public EmployeePermission() {
        super(Employee.class);
    }
}