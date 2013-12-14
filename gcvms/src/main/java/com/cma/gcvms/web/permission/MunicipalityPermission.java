package com.cma.gcvms.web.permission;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Municipality;
import com.cma.gcvms.web.permission.support.GenericPermission;

@Named
@Singleton
public class MunicipalityPermission extends GenericPermission<Municipality> {
    public MunicipalityPermission() {
        super(Municipality.class);
    }
}