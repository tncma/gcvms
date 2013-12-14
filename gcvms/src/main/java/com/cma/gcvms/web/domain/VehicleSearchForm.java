package com.cma.gcvms.web.domain;

import static com.cma.gcvms.repository.support.PropertySelector.newPropertySelector;
import static com.cma.gcvms.repository.support.Range.newRange;

import java.util.Date;

import javax.inject.Named;

import com.cma.gcvms.domain.Vehicle;
import com.cma.gcvms.domain.VehicleTyp;
import com.cma.gcvms.domain.Vehicle_;
import com.cma.gcvms.repository.support.PropertySelector;
import com.cma.gcvms.repository.support.Range;
import com.cma.gcvms.repository.support.SearchParameters;
import com.cma.gcvms.web.domain.support.GenericSearchForm;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper to search {@link Vehicle}.
 * It exposes a {@link Vehicle} instance so it can be used in search by-example-query.
 */
@Named
@ConversationContextScoped
public class VehicleSearchForm extends GenericSearchForm<Vehicle, Integer, VehicleSearchForm> {
    private static final long serialVersionUID = 1L;
    protected Vehicle vehicle = new Vehicle();
    protected Range<Vehicle, Integer> mileageRange = newRange(Vehicle_.mileage);
    protected Range<Vehicle, Integer> createdByRange = newRange(Vehicle_.createdBy);
    protected Range<Vehicle, Date> createdDateRange = newRange(Vehicle_.createdDate);
    protected PropertySelector<Vehicle, String> regNoSelector = newPropertySelector(Vehicle_.regNo);
    protected PropertySelector<Vehicle, Integer> mileageSelector = newPropertySelector(Vehicle_.mileage);
    protected PropertySelector<Vehicle, Integer> createdBySelector = newPropertySelector(Vehicle_.createdBy);
    protected PropertySelector<Vehicle, VehicleTyp> typSelector = newPropertySelector(Vehicle_.typ);

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    protected Vehicle getEntity() {
        return getVehicle();
    }

    @Override
    public VehicleSearchForm newInstance() {
        return new VehicleSearchForm();
    }

    @Override
    public SearchParameters toSearchParameters() {
        SearchParameters sp = searchParameters();
        sp.range(mileageRange, createdByRange, createdDateRange);
        sp.property(regNoSelector, mileageSelector, createdBySelector);
        sp.property(typSelector);
        return sp;
    }

    @Override
    public void resetWithOther(VehicleSearchForm other) {
        this.vehicle = other.getVehicle();
        this.mileageRange = other.getMileageRange();
        this.createdByRange = other.getCreatedByRange();
        this.createdDateRange = other.getCreatedDateRange();
        this.regNoSelector = other.getRegNoSelector();
        this.mileageSelector = other.getMileageSelector();
        this.createdBySelector = other.getCreatedBySelector();
        this.typSelector = other.getTypSelector();
    }

    // Ranges
    public Range<Vehicle, Integer> getMileageRange() {
        return mileageRange;
    }

    public Range<Vehicle, Integer> getCreatedByRange() {
        return createdByRange;
    }

    public Range<Vehicle, Date> getCreatedDateRange() {
        return createdDateRange;
    }

    // Property selectors
    public PropertySelector<Vehicle, String> getRegNoSelector() {
        return regNoSelector;
    }

    public PropertySelector<Vehicle, Integer> getMileageSelector() {
        return mileageSelector;
    }

    public PropertySelector<Vehicle, Integer> getCreatedBySelector() {
        return createdBySelector;
    }

    // Relation selectors
    public PropertySelector<Vehicle, VehicleTyp> getTypSelector() {
        return typSelector;
    }
}
