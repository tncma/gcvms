package com.cma.gcvms.web.permission;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Vehicle;
import com.cma.gcvms.web.permission.support.GenericPermission;

@Named
@Singleton
public class VehiclePermission extends GenericPermission<Vehicle> {
    public VehiclePermission() {
        super(Vehicle.class);
    }
}