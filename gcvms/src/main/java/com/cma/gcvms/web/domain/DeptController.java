package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Dept;
import com.cma.gcvms.printer.DeptPrinter;
import com.cma.gcvms.repository.DeptRepository;
import com.cma.gcvms.web.domain.support.GenericController;
import com.cma.gcvms.web.permission.DeptPermission;

/**
 * Stateless controller for {@link Dept} conversation start.
 */
@Named
@Singleton
public class DeptController extends GenericController<Dept, Integer> {
    public static final String DEPT_EDIT_URI = "/domain/deptEdit.faces";
    public static final String DEPT_SELECT_URI = "/domain/deptSelect.faces";

    @Inject
    public DeptController(DeptRepository deptRepository, DeptPermission deptPermission, DeptPrinter deptPrinter) {
        super(deptRepository, deptPermission, deptPrinter, DEPT_SELECT_URI, DEPT_EDIT_URI);
    }
}