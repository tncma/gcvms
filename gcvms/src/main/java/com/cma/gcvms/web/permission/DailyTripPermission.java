package com.cma.gcvms.web.permission;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.DailyTrip;
import com.cma.gcvms.web.permission.support.GenericPermission;

@Named
@Singleton
public class DailyTripPermission extends GenericPermission<DailyTrip> {
    public DailyTripPermission() {
        super(DailyTrip.class);
    }
}