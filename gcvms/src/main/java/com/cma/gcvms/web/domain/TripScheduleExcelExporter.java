package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import com.cma.gcvms.domain.TripSchedule;
import com.cma.gcvms.web.domain.support.GenericExcelExporter;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Exports to excel document {@link TripSchedule} search criteria and result. 
 */
@Named
@ConversationContextScoped
public class TripScheduleExcelExporter extends GenericExcelExporter<TripSchedule> {
    @Inject
    protected TripScheduleSearchForm sf;

    public TripScheduleExcelExporter() {
        super("tripSchedule_tripCnt", "tripSchedule_startDt", "tripSchedule_endDt", "tripSchedule_createdBy", "tripSchedule_createdDate");
    }

    @Override
    protected void fillResultItem(int row, TripSchedule item) {
        int col = 0;
        setValue(row, col++, item.getTripCnt());
        setValue(row, col++, item.getStartDt());
        setValue(row, col++, item.getEndDt());
        setValue(row, col++, item.getCreatedBy());
        setValue(row, col++, item.getCreatedDate());
    }

    @Override
    public void fillSearchCriteria(int row) {
        useCriteriaSheet();

        setSelectedEntities(row++, 0, "tripSchedule_vehicle", sf.getVehicleSelector().getSelected());
        setSelectedEntities(row++, 0, "tripSchedule_route", sf.getRouteSelector().getSelected());
        setRangeNumber(row++, 0, "tripSchedule_tripCnt", sf.getTripCntRange());
        setRangeDate(row++, 0, "tripSchedule_startDt", sf.getStartDtRange());
        setRangeDate(row++, 0, "tripSchedule_endDt", sf.getEndDtRange());
        setRangeNumber(row++, 0, "tripSchedule_createdBy", sf.getCreatedByRange());
        setRangeDate(row++, 0, "tripSchedule_createdDate", sf.getCreatedDateRange());
    }
}