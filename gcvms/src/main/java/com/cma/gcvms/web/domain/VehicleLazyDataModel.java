package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import com.cma.gcvms.domain.Vehicle;
import com.cma.gcvms.repository.VehicleRepository;
import com.cma.gcvms.web.domain.support.GenericLazyDataModel;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Provide PrimeFaces {@link LazyDataModel} for {@link Vehicle}
 */
@Named
@ConversationContextScoped
public class VehicleLazyDataModel extends GenericLazyDataModel<Vehicle, Integer, VehicleSearchForm> {
    private static final long serialVersionUID = 1L;

    @Inject
    public VehicleLazyDataModel(VehicleRepository vehicleRepository, VehicleController vehicleController, VehicleSearchForm vehicleSearchForm,
            VehicleExcelExporter vehicleExcelExporter) {
        super(vehicleRepository, vehicleController, vehicleSearchForm, vehicleExcelExporter);
    }
}