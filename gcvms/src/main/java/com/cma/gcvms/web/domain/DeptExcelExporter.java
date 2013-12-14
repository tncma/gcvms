package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import com.cma.gcvms.domain.Dept;
import com.cma.gcvms.web.domain.support.GenericExcelExporter;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Exports to excel document {@link Dept} search criteria and result. 
 */
@Named
@ConversationContextScoped
public class DeptExcelExporter extends GenericExcelExporter<Dept> {
    @Inject
    protected DeptSearchForm sf;

    public DeptExcelExporter() {
        super("dept_name");
    }

    @Override
    protected void fillResultItem(int row, Dept item) {
        int col = 0;
        setValue(row, col++, item.getName());
    }

    @Override
    public void fillSearchCriteria(int row) {
        useCriteriaSheet();

        setSelector(row++, 0, "dept_name", sf.getNameSelector());
    }
}