package com.cma.gcvms.web.domain;

import static com.cma.gcvms.repository.support.PropertySelector.newPropertySelector;

import javax.inject.Named;

import com.cma.gcvms.domain.Dept;
import com.cma.gcvms.domain.Dept_;
import com.cma.gcvms.repository.support.PropertySelector;
import com.cma.gcvms.repository.support.SearchParameters;
import com.cma.gcvms.web.domain.support.GenericSearchForm;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper to search {@link Dept}.
 * It exposes a {@link Dept} instance so it can be used in search by-example-query.
 */
@Named
@ConversationContextScoped
public class DeptSearchForm extends GenericSearchForm<Dept, Integer, DeptSearchForm> {
    private static final long serialVersionUID = 1L;
    protected Dept dept = new Dept();
    protected PropertySelector<Dept, String> nameSelector = newPropertySelector(Dept_.name);

    public Dept getDept() {
        return dept;
    }

    @Override
    protected Dept getEntity() {
        return getDept();
    }

    @Override
    public DeptSearchForm newInstance() {
        return new DeptSearchForm();
    }

    @Override
    public SearchParameters toSearchParameters() {
        SearchParameters sp = searchParameters();
        sp.property(nameSelector);
        return sp;
    }

    @Override
    public void resetWithOther(DeptSearchForm other) {
        this.dept = other.getDept();
        this.nameSelector = other.getNameSelector();
    }

    // Property selectors
    public PropertySelector<Dept, String> getNameSelector() {
        return nameSelector;
    }
}
