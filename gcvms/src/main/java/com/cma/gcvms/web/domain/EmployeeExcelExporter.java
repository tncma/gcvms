package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import com.cma.gcvms.domain.Employee;
import com.cma.gcvms.web.domain.support.GenericExcelExporter;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Exports to excel document {@link Employee} search criteria and result. 
 */
@Named
@ConversationContextScoped
public class EmployeeExcelExporter extends GenericExcelExporter<Employee> {
    @Inject
    protected EmployeeSearchForm sf;

    public EmployeeExcelExporter() {
        super("employee_fname", "employee_lname", "employee_pwd", "employee_designation");
    }

    @Override
    protected void fillResultItem(int row, Employee item) {
        int col = 0;
        setValue(row, col++, item.getFname());
        setValue(row, col++, item.getLname());
        setValue(row, col++, item.getPwd());
        setValue(row, col++, item.getDesignation());
    }

    @Override
    public void fillSearchCriteria(int row) {
        useCriteriaSheet();

        setSelector(row++, 0, "employee_fname", sf.getFnameSelector());
        setSelector(row++, 0, "employee_lname", sf.getLnameSelector());
        setSelector(row++, 0, "employee_pwd", sf.getPwdSelector());
        setSelectedEntities(row++, 0, "employee_dept", sf.getDeptSelector().getSelected());
        setSelectedEntities(row++, 0, "employee_mun", sf.getMunSelector().getSelected());
        setSelectedEntities(row++, 0, "employee_supervisor", sf.getSupervisorSelector().getSelected());
        setSelector(row++, 0, "employee_designation", sf.getDesignationSelector());
    }
}