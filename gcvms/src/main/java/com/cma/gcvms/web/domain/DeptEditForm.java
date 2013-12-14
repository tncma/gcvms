package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import com.cma.gcvms.domain.Dept;
import com.cma.gcvms.repository.DeptRepository;
import com.cma.gcvms.web.domain.support.GenericEditForm;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper/Controller to edit {@link Dept}.
 */
@Named
@ConversationContextScoped
public class DeptEditForm extends GenericEditForm<Dept, Integer> {
    @Inject
    protected DeptController deptController;

    @Inject
    public DeptEditForm(DeptRepository deptRepository) {
        super(deptRepository);
    }

    /**
     * The entity to edit/view.
     */
    public Dept getDept() {
        return getEntity();
    }

    public String print() {
        return deptController.print(getDept());
    }
}
