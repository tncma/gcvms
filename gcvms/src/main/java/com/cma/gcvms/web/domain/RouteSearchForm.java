package com.cma.gcvms.web.domain;

import static com.cma.gcvms.repository.support.PropertySelector.newPropertySelector;
import static com.cma.gcvms.repository.support.Range.newRange;

import java.util.Date;

import javax.inject.Named;

import com.cma.gcvms.domain.Route;
import com.cma.gcvms.domain.Route_;
import com.cma.gcvms.repository.support.PropertySelector;
import com.cma.gcvms.repository.support.Range;
import com.cma.gcvms.repository.support.SearchParameters;
import com.cma.gcvms.web.domain.support.GenericSearchForm;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper to search {@link Route}.
 * It exposes a {@link Route} instance so it can be used in search by-example-query.
 */
@Named
@ConversationContextScoped
public class RouteSearchForm extends GenericSearchForm<Route, Integer, RouteSearchForm> {
    private static final long serialVersionUID = 1L;
    protected Route route = new Route();
    protected Range<Route, Double> distanceRange = newRange(Route_.distance);
    protected Range<Route, Integer> createdByRange = newRange(Route_.createdBy);
    protected Range<Route, Date> createdDateRange = newRange(Route_.createdDate);
    protected PropertySelector<Route, String> nameSelector = newPropertySelector(Route_.name);
    protected PropertySelector<Route, Double> distanceSelector = newPropertySelector(Route_.distance);
    protected PropertySelector<Route, String> startLocSelector = newPropertySelector(Route_.startLoc);
    protected PropertySelector<Route, String> endLocSelector = newPropertySelector(Route_.endLoc);
    protected PropertySelector<Route, Integer> createdBySelector = newPropertySelector(Route_.createdBy);

    public Route getRoute() {
        return route;
    }

    @Override
    protected Route getEntity() {
        return getRoute();
    }

    @Override
    public RouteSearchForm newInstance() {
        return new RouteSearchForm();
    }

    @Override
    public SearchParameters toSearchParameters() {
        SearchParameters sp = searchParameters();
        sp.range(distanceRange, createdByRange, createdDateRange);
        sp.property(nameSelector, distanceSelector, startLocSelector, endLocSelector, createdBySelector);
        return sp;
    }

    @Override
    public void resetWithOther(RouteSearchForm other) {
        this.route = other.getRoute();
        this.distanceRange = other.getDistanceRange();
        this.createdByRange = other.getCreatedByRange();
        this.createdDateRange = other.getCreatedDateRange();
        this.nameSelector = other.getNameSelector();
        this.distanceSelector = other.getDistanceSelector();
        this.startLocSelector = other.getStartLocSelector();
        this.endLocSelector = other.getEndLocSelector();
        this.createdBySelector = other.getCreatedBySelector();
    }

    // Ranges
    public Range<Route, Double> getDistanceRange() {
        return distanceRange;
    }

    public Range<Route, Integer> getCreatedByRange() {
        return createdByRange;
    }

    public Range<Route, Date> getCreatedDateRange() {
        return createdDateRange;
    }

    // Property selectors
    public PropertySelector<Route, String> getNameSelector() {
        return nameSelector;
    }

    public PropertySelector<Route, Double> getDistanceSelector() {
        return distanceSelector;
    }

    public PropertySelector<Route, String> getStartLocSelector() {
        return startLocSelector;
    }

    public PropertySelector<Route, String> getEndLocSelector() {
        return endLocSelector;
    }

    public PropertySelector<Route, Integer> getCreatedBySelector() {
        return createdBySelector;
    }
}
