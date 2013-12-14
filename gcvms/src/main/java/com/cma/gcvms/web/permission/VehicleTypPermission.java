package com.cma.gcvms.web.permission;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.VehicleTyp;
import com.cma.gcvms.web.permission.support.GenericPermission;

@Named
@Singleton
public class VehicleTypPermission extends GenericPermission<VehicleTyp> {
    public VehicleTypPermission() {
        super(VehicleTyp.class);
    }
}