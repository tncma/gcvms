package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import com.cma.gcvms.domain.VehicleTyp;
import com.cma.gcvms.repository.VehicleTypRepository;
import com.cma.gcvms.web.domain.support.GenericLazyDataModel;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Provide PrimeFaces {@link LazyDataModel} for {@link VehicleTyp}
 */
@Named
@ConversationContextScoped
public class VehicleTypLazyDataModel extends GenericLazyDataModel<VehicleTyp, Integer, VehicleTypSearchForm> {
    private static final long serialVersionUID = 1L;

    @Inject
    public VehicleTypLazyDataModel(VehicleTypRepository vehicleTypRepository, VehicleTypController vehicleTypController,
            VehicleTypSearchForm vehicleTypSearchForm, VehicleTypExcelExporter vehicleTypExcelExporter) {
        super(vehicleTypRepository, vehicleTypController, vehicleTypSearchForm, vehicleTypExcelExporter);
    }
}