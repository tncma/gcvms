package com.cma.gcvms.util;

import java.util.Date;

public class DateUtil {
    /**
     * Compare 2 dates using seconds precision.
     * Useful for JPA equality concern when comparing Date from Java world (with millisec precision) with date
     * retrieved from Oracle (second precision only).
     */
    public boolean secondPrecisionDateEquality(Date date1, Date date2) {
        return secondPrecisionDate(date1).equals(secondPrecisionDate(date2));
    }

    public static String secondPrecisionDate(Date date) {
        String ret = Long.toString(date.getTime());
        return ret.substring(0, ret.length() - 3);
    }
}