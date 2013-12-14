package com.cma.gcvms.web.permission;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Route;
import com.cma.gcvms.web.permission.support.GenericPermission;

@Named
@Singleton
public class RoutePermission extends GenericPermission<Route> {
    public RoutePermission() {
        super(Route.class);
    }
}