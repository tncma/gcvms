package com.cma.gcvms.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Vehicle.class)
public abstract class Vehicle_ {

    // Raw attributes
    public static volatile SingularAttribute<Vehicle, Integer> id;
    public static volatile SingularAttribute<Vehicle, String> regNo;
    public static volatile SingularAttribute<Vehicle, Integer> mileage;
    public static volatile SingularAttribute<Vehicle, Integer> createdBy;
    public static volatile SingularAttribute<Vehicle, Date> createdDate;

    // Many to one
    public static volatile SingularAttribute<Vehicle, VehicleTyp> typ;
}