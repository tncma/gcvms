package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.TripSchedule;
import com.cma.gcvms.printer.TripSchedulePrinter;
import com.cma.gcvms.repository.TripScheduleRepository;
import com.cma.gcvms.web.domain.support.GenericController;
import com.cma.gcvms.web.permission.TripSchedulePermission;

/**
 * Stateless controller for {@link TripSchedule} conversation start.
 */
@Named
@Singleton
public class TripScheduleController extends GenericController<TripSchedule, Integer> {
    public static final String TRIPSCHEDULE_EDIT_URI = "/domain/tripScheduleEdit.faces";
    public static final String TRIPSCHEDULE_SELECT_URI = "/domain/tripScheduleSelect.faces";

    @Inject
    public TripScheduleController(TripScheduleRepository tripScheduleRepository, TripSchedulePermission tripSchedulePermission,
            TripSchedulePrinter tripSchedulePrinter) {
        super(tripScheduleRepository, tripSchedulePermission, tripSchedulePrinter, TRIPSCHEDULE_SELECT_URI, TRIPSCHEDULE_EDIT_URI);
    }
}