package com.cma.gcvms.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(VehicleTyp.class)
public abstract class VehicleTyp_ {

    // Raw attributes
    public static volatile SingularAttribute<VehicleTyp, Integer> id;
    public static volatile SingularAttribute<VehicleTyp, String> typName;
    public static volatile SingularAttribute<VehicleTyp, Double> capacity;
    public static volatile SingularAttribute<VehicleTyp, String> capUnit;
    public static volatile SingularAttribute<VehicleTyp, Integer> createdBy;
    public static volatile SingularAttribute<VehicleTyp, Date> createdDate;
}