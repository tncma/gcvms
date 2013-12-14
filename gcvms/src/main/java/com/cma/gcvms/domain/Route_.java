package com.cma.gcvms.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Route.class)
public abstract class Route_ {

    // Raw attributes
    public static volatile SingularAttribute<Route, Integer> id;
    public static volatile SingularAttribute<Route, String> name;
    public static volatile SingularAttribute<Route, Double> distance;
    public static volatile SingularAttribute<Route, String> startLoc;
    public static volatile SingularAttribute<Route, String> endLoc;
    public static volatile SingularAttribute<Route, Integer> createdBy;
    public static volatile SingularAttribute<Route, Date> createdDate;
}