package com.cma.gcvms.web.domain;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.cma.gcvms.domain.Dept;
import com.cma.gcvms.domain.Employee;
import com.cma.gcvms.domain.Employee_;
import com.cma.gcvms.domain.Municipality;
import com.cma.gcvms.repository.EmployeeRepository;
import com.cma.gcvms.web.domain.support.GenericEditForm;
import com.cma.gcvms.web.domain.support.GenericToOneAssociation;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper/Controller to edit {@link Employee}.
 */
@Named
@ConversationContextScoped
public class EmployeeEditForm extends GenericEditForm<Employee, Integer> {
    @Inject
    protected EmployeeController employeeController;
    @Inject
    protected MunicipalityController municipalityController;
    protected GenericToOneAssociation<Municipality, Integer> mun;
    protected GenericToOneAssociation<Employee, Integer> supervisor;
    @Inject
    protected DeptController deptController;
    protected GenericToOneAssociation<Dept, Integer> dept;

    @Inject
    public EmployeeEditForm(EmployeeRepository employeeRepository, EmployeeGraphLoader employeeGraphLoader) {
        super(employeeRepository, employeeGraphLoader);
    }

    /**
     * The entity to edit/view.
     */
    public Employee getEmployee() {
        return getEntity();
    }

    public String print() {
        return employeeController.print(getEmployee());
    }

    @PostConstruct
    void setupMunActions() {
        mun = new GenericToOneAssociation<Municipality, Integer>(municipalityController, Employee_.mun) {
            @Override
            protected Municipality get() {
                return getEmployee().getMun();
            }

            @Override
            protected void set(Municipality municipality) {
                getEmployee().setMun(municipality);
            }

            @NotNull
            @Override
            public Municipality getSelected() {
                return super.getSelected();
            }
        };
    }

    public GenericToOneAssociation<Municipality, Integer> getMun() {
        return mun;
    }

    @PostConstruct
    void setupSupervisorActions() {
        supervisor = new GenericToOneAssociation<Employee, Integer>(employeeController, Employee_.supervisor) {
            @Override
            protected Employee get() {
                return getEmployee().getSupervisor();
            }

            @Override
            protected void set(Employee employee) {
                getEmployee().setSupervisor(employee);
            }
        };
    }

    public GenericToOneAssociation<Employee, Integer> getSupervisor() {
        return supervisor;
    }

    @PostConstruct
    void setupDeptActions() {
        dept = new GenericToOneAssociation<Dept, Integer>(deptController, Employee_.dept) {
            @Override
            protected Dept get() {
                return getEmployee().getDept();
            }

            @Override
            protected void set(Dept dept) {
                getEmployee().setDept(dept);
            }

            @NotNull
            @Override
            public Dept getSelected() {
                return super.getSelected();
            }
        };
    }

    public GenericToOneAssociation<Dept, Integer> getDept() {
        return dept;
    }
}
