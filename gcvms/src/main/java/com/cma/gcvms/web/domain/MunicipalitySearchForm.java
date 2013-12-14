package com.cma.gcvms.web.domain;

import static com.cma.gcvms.repository.support.PropertySelector.newPropertySelector;

import javax.inject.Named;

import com.cma.gcvms.domain.Municipality;
import com.cma.gcvms.domain.Municipality_;
import com.cma.gcvms.repository.support.PropertySelector;
import com.cma.gcvms.repository.support.SearchParameters;
import com.cma.gcvms.web.domain.support.GenericSearchForm;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper to search {@link Municipality}.
 * It exposes a {@link Municipality} instance so it can be used in search by-example-query.
 */
@Named
@ConversationContextScoped
public class MunicipalitySearchForm extends GenericSearchForm<Municipality, Integer, MunicipalitySearchForm> {
    private static final long serialVersionUID = 1L;
    protected Municipality municipality = new Municipality();
    protected PropertySelector<Municipality, String> nameSelector = newPropertySelector(Municipality_.name);

    public Municipality getMunicipality() {
        return municipality;
    }

    @Override
    protected Municipality getEntity() {
        return getMunicipality();
    }

    @Override
    public MunicipalitySearchForm newInstance() {
        return new MunicipalitySearchForm();
    }

    @Override
    public SearchParameters toSearchParameters() {
        SearchParameters sp = searchParameters();
        sp.property(nameSelector);
        return sp;
    }

    @Override
    public void resetWithOther(MunicipalitySearchForm other) {
        this.municipality = other.getMunicipality();
        this.nameSelector = other.getNameSelector();
    }

    // Property selectors
    public PropertySelector<Municipality, String> getNameSelector() {
        return nameSelector;
    }
}
