package com.cma.gcvms.web.component;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.LONG;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import java.util.Calendar;
import java.util.List;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.context.LocaleHolder;

/**
 * Helper used from the {@link DatePicker} composite component.
 */
@Named
@Singleton
public class DatePickerHelper {

    public List<SelectItem> getYears(int min, int max) {
        List<SelectItem> result = newArrayList();
        for (int i = min; i <= max; i++) {
            String year = String.valueOf(i);
            result.add(new SelectItem(year, year));
        }

        return result;
    }

    public List<SelectItem> getMonths() {
        List<SelectItem> result = newArrayList();

        Calendar c = Calendar.getInstance();
        c.set(DAY_OF_MONTH, 1); // prevent potential month shifting when reseting month below

        for (int i = 1; i <= 12; i++) {
            c.set(MONTH, i - 1);
            String label = c.getDisplayName(MONTH, LONG, LocaleHolder.getLocale());
            result.add(new SelectItem(normalize(i), label));
        }

        return result;
    }

    public List<SelectItem> getDays(String ccClientId, boolean appendDayOfWeek) {
        UIInput ccDatepicker = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(ccClientId);
        UIInput year = (UIInput) ccDatepicker.findComponent("year");
        UIInput month = (UIInput) ccDatepicker.findComponent("month");

        Calendar c = Calendar.getInstance();
        c.set(DAY_OF_MONTH, 1); // prevent potential month shifting when reseting month below
        try {
            c.set(YEAR, Integer.parseInt((String) year.getLocalValue()));
            c.set(MONTH, Integer.parseInt((String) month.getLocalValue()) - 1);
        } catch (Exception e) {
            //
        }

        List<SelectItem> result = newArrayList();

        int max = c.getActualMaximum(DAY_OF_MONTH);
        for (int i = 1; i <= max; i++) {
            String day = normalize(i);
            c.set(DAY_OF_MONTH, i);
            StringBuilder sb = new StringBuilder();
            sb.append(day);
            if (appendDayOfWeek) {
                sb.append(" ").append(c.getDisplayName(DAY_OF_WEEK, LONG, LocaleHolder.getLocale()));
            }
            result.add(new SelectItem(day, sb.toString()));
        }

        return result;
    }

    public List<SelectItem> getHours() {
        List<SelectItem> result = newArrayList();
        for (int i = 0; i < 24; i++) {
            String hour = normalize(i);
            result.add(new SelectItem(hour, hour));
        }
        return result;
    }

    public List<SelectItem> getMinutes() {
        List<SelectItem> result = newArrayList();
        result.add(new SelectItem("00", "00"));
        result.add(new SelectItem("15", "15"));
        result.add(new SelectItem("30", "30"));
        result.add(new SelectItem("45", "45"));
        return result;
    }

    public static final String normalize(int i) {
        return i < 10 ? "0" + i : "" + i;
    }
}