package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Vehicle;
import com.cma.gcvms.domain.Vehicle_;
import com.cma.gcvms.printer.VehiclePrinter;
import com.cma.gcvms.repository.VehicleRepository;
import com.cma.gcvms.repository.support.SearchParameters;
import com.cma.gcvms.web.domain.support.GenericController;
import com.cma.gcvms.web.permission.VehiclePermission;

/**
 * Stateless controller for {@link Vehicle} conversation start.
 */
@Named
@Singleton
public class VehicleController extends GenericController<Vehicle, Integer> {
    public static final String VEHICLE_EDIT_URI = "/domain/vehicleEdit.faces";
    public static final String VEHICLE_SELECT_URI = "/domain/vehicleSelect.faces";

    @Inject
    public VehicleController(VehicleRepository vehicleRepository, VehiclePermission vehiclePermission, VehiclePrinter vehiclePrinter) {
        super(vehicleRepository, vehiclePermission, vehiclePrinter, VEHICLE_SELECT_URI, VEHICLE_EDIT_URI);
    }

    @Override
    protected SearchParameters defaultOrder(SearchParameters searchParameters) {
        return searchParameters.asc(Vehicle_.regNo);
    }
}