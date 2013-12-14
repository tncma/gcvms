package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.VehicleTyp;
import com.cma.gcvms.printer.VehicleTypPrinter;
import com.cma.gcvms.repository.VehicleTypRepository;
import com.cma.gcvms.web.domain.support.GenericController;
import com.cma.gcvms.web.permission.VehicleTypPermission;

/**
 * Stateless controller for {@link VehicleTyp} conversation start.
 */
@Named
@Singleton
public class VehicleTypController extends GenericController<VehicleTyp, Integer> {
    public static final String VEHICLETYP_EDIT_URI = "/domain/vehicleTypEdit.faces";
    public static final String VEHICLETYP_SELECT_URI = "/domain/vehicleTypSelect.faces";

    @Inject
    public VehicleTypController(VehicleTypRepository vehicleTypRepository, VehicleTypPermission vehicleTypPermission, VehicleTypPrinter vehicleTypPrinter) {
        super(vehicleTypRepository, vehicleTypPermission, vehicleTypPrinter, VEHICLETYP_SELECT_URI, VEHICLETYP_EDIT_URI);
    }
}