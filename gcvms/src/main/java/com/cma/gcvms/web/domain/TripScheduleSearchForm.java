package com.cma.gcvms.web.domain;

import static com.cma.gcvms.repository.support.PropertySelector.newPropertySelector;
import static com.cma.gcvms.repository.support.Range.newRange;

import java.util.Date;

import javax.inject.Named;

import com.cma.gcvms.domain.Route;
import com.cma.gcvms.domain.TripSchedule;
import com.cma.gcvms.domain.TripSchedule_;
import com.cma.gcvms.domain.Vehicle;
import com.cma.gcvms.repository.support.PropertySelector;
import com.cma.gcvms.repository.support.Range;
import com.cma.gcvms.repository.support.SearchParameters;
import com.cma.gcvms.web.domain.support.GenericSearchForm;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper to search {@link TripSchedule}.
 * It exposes a {@link TripSchedule} instance so it can be used in search by-example-query.
 */
@Named
@ConversationContextScoped
public class TripScheduleSearchForm extends GenericSearchForm<TripSchedule, Integer, TripScheduleSearchForm> {
    private static final long serialVersionUID = 1L;
    protected TripSchedule tripSchedule = new TripSchedule();
    protected Range<TripSchedule, Integer> tripCntRange = newRange(TripSchedule_.tripCnt);
    protected Range<TripSchedule, Date> startDtRange = newRange(TripSchedule_.startDt);
    protected Range<TripSchedule, Date> endDtRange = newRange(TripSchedule_.endDt);
    protected Range<TripSchedule, Integer> createdByRange = newRange(TripSchedule_.createdBy);
    protected Range<TripSchedule, Date> createdDateRange = newRange(TripSchedule_.createdDate);
    protected PropertySelector<TripSchedule, Integer> tripCntSelector = newPropertySelector(TripSchedule_.tripCnt);
    protected PropertySelector<TripSchedule, Integer> createdBySelector = newPropertySelector(TripSchedule_.createdBy);
    protected PropertySelector<TripSchedule, Route> routeSelector = newPropertySelector(TripSchedule_.route);
    protected PropertySelector<TripSchedule, Vehicle> vehicleSelector = newPropertySelector(TripSchedule_.vehicle);

    public TripSchedule getTripSchedule() {
        return tripSchedule;
    }

    @Override
    protected TripSchedule getEntity() {
        return getTripSchedule();
    }

    @Override
    public TripScheduleSearchForm newInstance() {
        return new TripScheduleSearchForm();
    }

    @Override
    public SearchParameters toSearchParameters() {
        SearchParameters sp = searchParameters();
        sp.range(tripCntRange, startDtRange, endDtRange, createdByRange, createdDateRange);
        sp.property(tripCntSelector, createdBySelector);
        sp.property(routeSelector, vehicleSelector);
        return sp;
    }

    @Override
    public void resetWithOther(TripScheduleSearchForm other) {
        this.tripSchedule = other.getTripSchedule();
        this.tripCntRange = other.getTripCntRange();
        this.startDtRange = other.getStartDtRange();
        this.endDtRange = other.getEndDtRange();
        this.createdByRange = other.getCreatedByRange();
        this.createdDateRange = other.getCreatedDateRange();
        this.tripCntSelector = other.getTripCntSelector();
        this.createdBySelector = other.getCreatedBySelector();
        this.routeSelector = other.getRouteSelector();
        this.vehicleSelector = other.getVehicleSelector();
    }

    // Ranges
    public Range<TripSchedule, Integer> getTripCntRange() {
        return tripCntRange;
    }

    public Range<TripSchedule, Date> getStartDtRange() {
        return startDtRange;
    }

    public Range<TripSchedule, Date> getEndDtRange() {
        return endDtRange;
    }

    public Range<TripSchedule, Integer> getCreatedByRange() {
        return createdByRange;
    }

    public Range<TripSchedule, Date> getCreatedDateRange() {
        return createdDateRange;
    }

    // Property selectors
    public PropertySelector<TripSchedule, Integer> getTripCntSelector() {
        return tripCntSelector;
    }

    public PropertySelector<TripSchedule, Integer> getCreatedBySelector() {
        return createdBySelector;
    }

    // Relation selectors
    public PropertySelector<TripSchedule, Route> getRouteSelector() {
        return routeSelector;
    }

    public PropertySelector<TripSchedule, Vehicle> getVehicleSelector() {
        return vehicleSelector;
    }
}
