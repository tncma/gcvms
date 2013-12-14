package com.cma.gcvms.printer;

import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Vehicle;
import com.cma.gcvms.domain.Vehicle_;
import com.cma.gcvms.printer.support.GenericPrinter;

/**
 * {@link GenericPrinter} for {@link Vehicle} 
 *
 * @see GenericPrinter
 * @see TypeAwarePrinter
 */
@Named
@Singleton
public class VehiclePrinter extends GenericPrinter<Vehicle> {
    public VehiclePrinter() {
        super(Vehicle.class, Vehicle_.regNo);
    }

    @Override
    public String print(Vehicle vehicle, Locale locale) {
        if (vehicle == null) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        appendIfNotEmpty(ret, vehicle.getRegNo());
        return ret.toString();
    }
}