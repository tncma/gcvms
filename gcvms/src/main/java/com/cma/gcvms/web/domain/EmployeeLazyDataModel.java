package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import com.cma.gcvms.domain.Employee;
import com.cma.gcvms.repository.EmployeeRepository;
import com.cma.gcvms.web.domain.support.GenericLazyDataModel;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Provide PrimeFaces {@link LazyDataModel} for {@link Employee}
 */
@Named
@ConversationContextScoped
public class EmployeeLazyDataModel extends GenericLazyDataModel<Employee, Integer, EmployeeSearchForm> {
    private static final long serialVersionUID = 1L;

    @Inject
    public EmployeeLazyDataModel(EmployeeRepository employeeRepository, EmployeeController employeeController, EmployeeSearchForm employeeSearchForm,
            EmployeeExcelExporter employeeExcelExporter) {
        super(employeeRepository, employeeController, employeeSearchForm, employeeExcelExporter);
    }
}