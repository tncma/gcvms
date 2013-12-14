package com.cma.gcvms.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TripSchedule.class)
public abstract class TripSchedule_ {

    // Raw attributes
    public static volatile SingularAttribute<TripSchedule, Integer> id;
    public static volatile SingularAttribute<TripSchedule, Integer> tripCnt;
    public static volatile SingularAttribute<TripSchedule, Date> startDt;
    public static volatile SingularAttribute<TripSchedule, Date> endDt;
    public static volatile SingularAttribute<TripSchedule, Integer> createdBy;
    public static volatile SingularAttribute<TripSchedule, Date> createdDate;

    // Many to one
    public static volatile SingularAttribute<TripSchedule, Route> route;
    public static volatile SingularAttribute<TripSchedule, Vehicle> vehicle;
}