package com.cma.gcvms.web.permission;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.TripSchedule;
import com.cma.gcvms.web.permission.support.GenericPermission;

@Named
@Singleton
public class TripSchedulePermission extends GenericPermission<TripSchedule> {
    public TripSchedulePermission() {
        super(TripSchedule.class);
    }
}