package com.cma.gcvms.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Dept.class)
public abstract class Dept_ {

    // Raw attributes
    public static volatile SingularAttribute<Dept, Integer> id;
    public static volatile SingularAttribute<Dept, String> name;
}