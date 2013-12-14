package com.cma.gcvms.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Municipality.class)
public abstract class Municipality_ {

    // Raw attributes
    public static volatile SingularAttribute<Municipality, Integer> id;
    public static volatile SingularAttribute<Municipality, String> name;
}