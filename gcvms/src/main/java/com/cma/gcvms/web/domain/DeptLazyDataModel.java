package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import com.cma.gcvms.domain.Dept;
import com.cma.gcvms.repository.DeptRepository;
import com.cma.gcvms.web.domain.support.GenericLazyDataModel;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Provide PrimeFaces {@link LazyDataModel} for {@link Dept}
 */
@Named
@ConversationContextScoped
public class DeptLazyDataModel extends GenericLazyDataModel<Dept, Integer, DeptSearchForm> {
    private static final long serialVersionUID = 1L;

    @Inject
    public DeptLazyDataModel(DeptRepository deptRepository, DeptController deptController, DeptSearchForm deptSearchForm, DeptExcelExporter deptExcelExporter) {
        super(deptRepository, deptController, deptSearchForm, deptExcelExporter);
    }
}