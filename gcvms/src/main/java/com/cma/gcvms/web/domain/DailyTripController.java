package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.DailyTrip;
import com.cma.gcvms.printer.DailyTripPrinter;
import com.cma.gcvms.repository.DailyTripRepository;
import com.cma.gcvms.web.domain.support.GenericController;
import com.cma.gcvms.web.permission.DailyTripPermission;

/**
 * Stateless controller for {@link DailyTrip} conversation start.
 */
@Named
@Singleton
public class DailyTripController extends GenericController<DailyTrip, Integer> {
    public static final String DAILYTRIP_EDIT_URI = "/domain/dailyTripEdit.faces";
    public static final String DAILYTRIP_SELECT_URI = "/domain/dailyTripSelect.faces";

    @Inject
    public DailyTripController(DailyTripRepository dailyTripRepository, DailyTripPermission dailyTripPermission, DailyTripPrinter dailyTripPrinter) {
        super(dailyTripRepository, dailyTripPermission, dailyTripPrinter, DAILYTRIP_SELECT_URI, DAILYTRIP_EDIT_URI);
    }
}