package com.cma.gcvms.repository;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Employee;
import com.cma.gcvms.repository.support.GenericRepository;

/**
 * {@link GenericRepository} for {@link Employee} 
 */
@Named
@Singleton
public class EmployeeRepository extends GenericRepository<Employee, Integer> {

    public EmployeeRepository() {
        super(Employee.class);
    }

    @Override
    public Employee getNew() {
        return new Employee();
    }

    @Override
    public Employee getNewWithDefaults() {
        return getNew().withDefaults();
    }
}