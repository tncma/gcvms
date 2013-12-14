package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import com.cma.gcvms.domain.Municipality;
import com.cma.gcvms.web.domain.support.GenericExcelExporter;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Exports to excel document {@link Municipality} search criteria and result. 
 */
@Named
@ConversationContextScoped
public class MunicipalityExcelExporter extends GenericExcelExporter<Municipality> {
    @Inject
    protected MunicipalitySearchForm sf;

    public MunicipalityExcelExporter() {
        super("municipality_name");
    }

    @Override
    protected void fillResultItem(int row, Municipality item) {
        int col = 0;
        setValue(row, col++, item.getName());
    }

    @Override
    public void fillSearchCriteria(int row) {
        useCriteriaSheet();

        setSelector(row++, 0, "municipality_name", sf.getNameSelector());
    }
}