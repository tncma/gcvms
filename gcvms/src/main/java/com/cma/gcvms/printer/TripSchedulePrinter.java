package com.cma.gcvms.printer;

import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.TripSchedule;
import com.cma.gcvms.domain.TripSchedule_;
import com.cma.gcvms.printer.support.GenericPrinter;

/**
 * {@link GenericPrinter} for {@link TripSchedule} 
 *
 * @see GenericPrinter
 * @see TypeAwarePrinter
 */
@Named
@Singleton
public class TripSchedulePrinter extends GenericPrinter<TripSchedule> {
    public TripSchedulePrinter() {
        super(TripSchedule.class, TripSchedule_.tripCnt, TripSchedule_.startDt, TripSchedule_.endDt);
    }

    @Override
    public String print(TripSchedule tripSchedule, Locale locale) {
        if (tripSchedule == null) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        appendIfNotEmpty(ret, tripSchedule.getTripCnt());
        appendIfNotEmpty(ret, tripSchedule.getStartDt());
        appendIfNotEmpty(ret, tripSchedule.getEndDt());
        return ret.toString();
    }
}