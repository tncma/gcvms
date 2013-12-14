package com.cma.gcvms.printer;

import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.VehicleTyp;
import com.cma.gcvms.domain.VehicleTyp_;
import com.cma.gcvms.printer.support.GenericPrinter;

/**
 * {@link GenericPrinter} for {@link VehicleTyp} 
 *
 * @see GenericPrinter
 * @see TypeAwarePrinter
 */
@Named
@Singleton
public class VehicleTypPrinter extends GenericPrinter<VehicleTyp> {
    public VehicleTypPrinter() {
        super(VehicleTyp.class, VehicleTyp_.typName);
    }

    @Override
    public String print(VehicleTyp vehicleTyp, Locale locale) {
        if (vehicleTyp == null) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        appendIfNotEmpty(ret, vehicleTyp.getTypName());
        return ret.toString();
    }
}