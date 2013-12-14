package com.cma.gcvms.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DailyTrip.class)
public abstract class DailyTrip_ {

    // Raw attributes
    public static volatile SingularAttribute<DailyTrip, Integer> id;
    public static volatile SingularAttribute<DailyTrip, Date> tripDate;
    public static volatile SingularAttribute<DailyTrip, Date> tripTime;

    // Many to one
    public static volatile SingularAttribute<DailyTrip, Vehicle> vehicle;
    public static volatile SingularAttribute<DailyTrip, Route> route;
    public static volatile SingularAttribute<DailyTrip, Employee> supervisor;
}