package com.cma.gcvms.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Employee.class)
public abstract class Employee_ {

    // Raw attributes
    public static volatile SingularAttribute<Employee, Integer> id;
    public static volatile SingularAttribute<Employee, String> fname;
    public static volatile SingularAttribute<Employee, String> lname;
    public static volatile SingularAttribute<Employee, String> pwd;
    public static volatile SingularAttribute<Employee, String> designation;

    // Many to one
    public static volatile SingularAttribute<Employee, Municipality> mun;
    public static volatile SingularAttribute<Employee, Employee> supervisor;
    public static volatile SingularAttribute<Employee, Dept> dept;
}