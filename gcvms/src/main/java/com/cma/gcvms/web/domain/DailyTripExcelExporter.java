package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import com.cma.gcvms.domain.DailyTrip;
import com.cma.gcvms.web.domain.support.GenericExcelExporter;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Exports to excel document {@link DailyTrip} search criteria and result. 
 */
@Named
@ConversationContextScoped
public class DailyTripExcelExporter extends GenericExcelExporter<DailyTrip> {
    @Inject
    protected DailyTripSearchForm sf;

    public DailyTripExcelExporter() {
        super("dailyTrip_tripDate", "dailyTrip_tripTime");
    }

    @Override
    protected void fillResultItem(int row, DailyTrip item) {
        int col = 0;
        setValue(row, col++, item.getTripDate());
        setDateTime(row, col++, item.getTripTime());
    }

    @Override
    public void fillSearchCriteria(int row) {
        useCriteriaSheet();

        setRangeDate(row++, 0, "dailyTrip_tripDate", sf.getTripDateRange());
        setSelectedEntities(row++, 0, "dailyTrip_vehicle", sf.getVehicleSelector().getSelected());
        setSelectedEntities(row++, 0, "dailyTrip_route", sf.getRouteSelector().getSelected());
        setRangeDateTime(row++, 0, "dailyTrip_tripTime", sf.getTripTimeRange());
        setSelectedEntities(row++, 0, "dailyTrip_supervisor", sf.getSupervisorSelector().getSelected());
    }
}