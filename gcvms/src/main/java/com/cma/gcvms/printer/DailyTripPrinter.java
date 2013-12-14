package com.cma.gcvms.printer;

import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.DailyTrip;
import com.cma.gcvms.domain.DailyTrip_;
import com.cma.gcvms.printer.support.GenericPrinter;

/**
 * {@link GenericPrinter} for {@link DailyTrip} 
 *
 * @see GenericPrinter
 * @see TypeAwarePrinter
 */
@Named
@Singleton
public class DailyTripPrinter extends GenericPrinter<DailyTrip> {
    public DailyTripPrinter() {
        super(DailyTrip.class, DailyTrip_.tripDate, DailyTrip_.tripTime);
    }

    @Override
    public String print(DailyTrip dailyTrip, Locale locale) {
        if (dailyTrip == null) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        appendIfNotEmpty(ret, dailyTrip.getTripDate());
        appendIfNotEmpty(ret, dailyTrip.getTripTime());
        return ret.toString();
    }
}