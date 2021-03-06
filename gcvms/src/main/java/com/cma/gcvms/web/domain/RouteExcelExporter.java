package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import com.cma.gcvms.domain.Route;
import com.cma.gcvms.web.domain.support.GenericExcelExporter;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Exports to excel document {@link Route} search criteria and result. 
 */
@Named
@ConversationContextScoped
public class RouteExcelExporter extends GenericExcelExporter<Route> {
    @Inject
    protected RouteSearchForm sf;

    public RouteExcelExporter() {
        super("route_name", "route_distance", "route_startLoc", "route_endLoc", "route_createdBy", "route_createdDate");
    }

    @Override
    protected void fillResultItem(int row, Route item) {
        int col = 0;
        setValue(row, col++, item.getName());
        setValue(row, col++, item.getDistance());
        setValue(row, col++, item.getStartLoc());
        setValue(row, col++, item.getEndLoc());
        setValue(row, col++, item.getCreatedBy());
        setValue(row, col++, item.getCreatedDate());
    }

    @Override
    public void fillSearchCriteria(int row) {
        useCriteriaSheet();

        setSelector(row++, 0, "route_name", sf.getNameSelector());
        setRangeNumber(row++, 0, "route_distance", sf.getDistanceRange());
        setSelector(row++, 0, "route_startLoc", sf.getStartLocSelector());
        setSelector(row++, 0, "route_endLoc", sf.getEndLocSelector());
        setRangeNumber(row++, 0, "route_createdBy", sf.getCreatedByRange());
        setRangeDate(row++, 0, "route_createdDate", sf.getCreatedDateRange());
    }
}