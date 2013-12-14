package com.cma.gcvms.web.permission;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Dept;
import com.cma.gcvms.web.permission.support.GenericPermission;

@Named
@Singleton
public class DeptPermission extends GenericPermission<Dept> {
    public DeptPermission() {
        super(Dept.class);
    }
}