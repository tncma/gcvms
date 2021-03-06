package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import com.cma.gcvms.domain.Vehicle;
import com.cma.gcvms.web.domain.support.GenericExcelExporter;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Exports to excel document {@link Vehicle} search criteria and result. 
 */
@Named
@ConversationContextScoped
public class VehicleExcelExporter extends GenericExcelExporter<Vehicle> {
    @Inject
    protected VehicleSearchForm sf;

    public VehicleExcelExporter() {
        super("vehicle_regNo", "vehicle_mileage", "vehicle_createdBy", "vehicle_createdDate");
    }

    @Override
    protected void fillResultItem(int row, Vehicle item) {
        int col = 0;
        setValue(row, col++, item.getRegNo());
        setValue(row, col++, item.getMileage());
        setValue(row, col++, item.getCreatedBy());
        setValue(row, col++, item.getCreatedDate());
    }

    @Override
    public void fillSearchCriteria(int row) {
        useCriteriaSheet();

        setSelector(row++, 0, "vehicle_regNo", sf.getRegNoSelector());
        setSelectedEntities(row++, 0, "vehicle_typ", sf.getTypSelector().getSelected());
        setRangeNumber(row++, 0, "vehicle_mileage", sf.getMileageRange());
        setRangeNumber(row++, 0, "vehicle_createdBy", sf.getCreatedByRange());
        setRangeDate(row++, 0, "vehicle_createdDate", sf.getCreatedDateRange());
    }
}