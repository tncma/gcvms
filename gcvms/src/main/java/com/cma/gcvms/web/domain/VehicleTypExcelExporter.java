package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import com.cma.gcvms.domain.VehicleTyp;
import com.cma.gcvms.web.domain.support.GenericExcelExporter;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Exports to excel document {@link VehicleTyp} search criteria and result. 
 */
@Named
@ConversationContextScoped
public class VehicleTypExcelExporter extends GenericExcelExporter<VehicleTyp> {
    @Inject
    protected VehicleTypSearchForm sf;

    public VehicleTypExcelExporter() {
        super("vehicleTyp_typName", "vehicleTyp_capacity", "vehicleTyp_capUnit", "vehicleTyp_createdBy", "vehicleTyp_createdDate");
    }

    @Override
    protected void fillResultItem(int row, VehicleTyp item) {
        int col = 0;
        setValue(row, col++, item.getTypName());
        setValue(row, col++, item.getCapacity());
        setValue(row, col++, item.getCapUnit());
        setValue(row, col++, item.getCreatedBy());
        setValue(row, col++, item.getCreatedDate());
    }

    @Override
    public void fillSearchCriteria(int row) {
        useCriteriaSheet();

        setSelector(row++, 0, "vehicleTyp_typName", sf.getTypNameSelector());
        setRangeNumber(row++, 0, "vehicleTyp_capacity", sf.getCapacityRange());
        setSelector(row++, 0, "vehicleTyp_capUnit", sf.getCapUnitSelector());
        setRangeNumber(row++, 0, "vehicleTyp_createdBy", sf.getCreatedByRange());
        setRangeDate(row++, 0, "vehicleTyp_createdDate", sf.getCreatedDateRange());
    }
}