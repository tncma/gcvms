package com.cma.gcvms.web.domain;

import static com.cma.gcvms.repository.support.PropertySelector.newPropertySelector;

import javax.inject.Named;

import com.cma.gcvms.domain.Dept;
import com.cma.gcvms.domain.Employee;
import com.cma.gcvms.domain.Employee_;
import com.cma.gcvms.domain.Municipality;
import com.cma.gcvms.repository.support.PropertySelector;
import com.cma.gcvms.repository.support.SearchParameters;
import com.cma.gcvms.web.domain.support.GenericSearchForm;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper to search {@link Employee}.
 * It exposes a {@link Employee} instance so it can be used in search by-example-query.
 */
@Named
@ConversationContextScoped
public class EmployeeSearchForm extends GenericSearchForm<Employee, Integer, EmployeeSearchForm> {
    private static final long serialVersionUID = 1L;
    protected Employee employee = new Employee();
    protected PropertySelector<Employee, String> fnameSelector = newPropertySelector(Employee_.fname);
    protected PropertySelector<Employee, String> lnameSelector = newPropertySelector(Employee_.lname);
    protected PropertySelector<Employee, String> pwdSelector = newPropertySelector(Employee_.pwd);
    protected PropertySelector<Employee, String> designationSelector = newPropertySelector(Employee_.designation);
    protected PropertySelector<Employee, Municipality> munSelector = newPropertySelector(Employee_.mun);
    protected PropertySelector<Employee, Employee> supervisorSelector = newPropertySelector(Employee_.supervisor);
    protected PropertySelector<Employee, Dept> deptSelector = newPropertySelector(Employee_.dept);

    public Employee getEmployee() {
        return employee;
    }

    @Override
    protected Employee getEntity() {
        return getEmployee();
    }

    @Override
    public EmployeeSearchForm newInstance() {
        return new EmployeeSearchForm();
    }

    @Override
    public SearchParameters toSearchParameters() {
        SearchParameters sp = searchParameters();
        sp.property(fnameSelector, lnameSelector, pwdSelector, designationSelector);
        sp.property(munSelector, supervisorSelector, deptSelector);
        return sp;
    }

    @Override
    public void resetWithOther(EmployeeSearchForm other) {
        this.employee = other.getEmployee();
        this.fnameSelector = other.getFnameSelector();
        this.lnameSelector = other.getLnameSelector();
        this.pwdSelector = other.getPwdSelector();
        this.designationSelector = other.getDesignationSelector();
        this.munSelector = other.getMunSelector();
        this.supervisorSelector = other.getSupervisorSelector();
        this.deptSelector = other.getDeptSelector();
    }

    // Property selectors
    public PropertySelector<Employee, String> getFnameSelector() {
        return fnameSelector;
    }

    public PropertySelector<Employee, String> getLnameSelector() {
        return lnameSelector;
    }

    public PropertySelector<Employee, String> getPwdSelector() {
        return pwdSelector;
    }

    public PropertySelector<Employee, String> getDesignationSelector() {
        return designationSelector;
    }

    // Relation selectors
    public PropertySelector<Employee, Municipality> getMunSelector() {
        return munSelector;
    }

    public PropertySelector<Employee, Employee> getSupervisorSelector() {
        return supervisorSelector;
    }

    public PropertySelector<Employee, Dept> getDeptSelector() {
        return deptSelector;
    }
}
