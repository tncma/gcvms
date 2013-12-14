package com.cma.gcvms.web.domain;

import static com.cma.gcvms.repository.support.PropertySelector.newPropertySelector;
import static com.cma.gcvms.repository.support.Range.newRange;

import java.util.Date;

import javax.inject.Named;

import com.cma.gcvms.domain.VehicleTyp;
import com.cma.gcvms.domain.VehicleTyp_;
import com.cma.gcvms.repository.support.PropertySelector;
import com.cma.gcvms.repository.support.Range;
import com.cma.gcvms.repository.support.SearchParameters;
import com.cma.gcvms.web.domain.support.GenericSearchForm;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper to search {@link VehicleTyp}.
 * It exposes a {@link VehicleTyp} instance so it can be used in search by-example-query.
 */
@Named
@ConversationContextScoped
public class VehicleTypSearchForm extends GenericSearchForm<VehicleTyp, Integer, VehicleTypSearchForm> {
    private static final long serialVersionUID = 1L;
    protected VehicleTyp vehicleTyp = new VehicleTyp();
    protected Range<VehicleTyp, Double> capacityRange = newRange(VehicleTyp_.capacity);
    protected Range<VehicleTyp, Integer> createdByRange = newRange(VehicleTyp_.createdBy);
    protected Range<VehicleTyp, Date> createdDateRange = newRange(VehicleTyp_.createdDate);
    protected PropertySelector<VehicleTyp, String> typNameSelector = newPropertySelector(VehicleTyp_.typName);
    protected PropertySelector<VehicleTyp, Double> capacitySelector = newPropertySelector(VehicleTyp_.capacity);
    protected PropertySelector<VehicleTyp, String> capUnitSelector = newPropertySelector(VehicleTyp_.capUnit);
    protected PropertySelector<VehicleTyp, Integer> createdBySelector = newPropertySelector(VehicleTyp_.createdBy);

    public VehicleTyp getVehicleTyp() {
        return vehicleTyp;
    }

    @Override
    protected VehicleTyp getEntity() {
        return getVehicleTyp();
    }

    @Override
    public VehicleTypSearchForm newInstance() {
        return new VehicleTypSearchForm();
    }

    @Override
    public SearchParameters toSearchParameters() {
        SearchParameters sp = searchParameters();
        sp.range(capacityRange, createdByRange, createdDateRange);
        sp.property(typNameSelector, capacitySelector, capUnitSelector, createdBySelector);
        return sp;
    }

    @Override
    public void resetWithOther(VehicleTypSearchForm other) {
        this.vehicleTyp = other.getVehicleTyp();
        this.capacityRange = other.getCapacityRange();
        this.createdByRange = other.getCreatedByRange();
        this.createdDateRange = other.getCreatedDateRange();
        this.typNameSelector = other.getTypNameSelector();
        this.capacitySelector = other.getCapacitySelector();
        this.capUnitSelector = other.getCapUnitSelector();
        this.createdBySelector = other.getCreatedBySelector();
    }

    // Ranges
    public Range<VehicleTyp, Double> getCapacityRange() {
        return capacityRange;
    }

    public Range<VehicleTyp, Integer> getCreatedByRange() {
        return createdByRange;
    }

    public Range<VehicleTyp, Date> getCreatedDateRange() {
        return createdDateRange;
    }

    // Property selectors
    public PropertySelector<VehicleTyp, String> getTypNameSelector() {
        return typNameSelector;
    }

    public PropertySelector<VehicleTyp, Double> getCapacitySelector() {
        return capacitySelector;
    }

    public PropertySelector<VehicleTyp, String> getCapUnitSelector() {
        return capUnitSelector;
    }

    public PropertySelector<VehicleTyp, Integer> getCreatedBySelector() {
        return createdBySelector;
    }
}
