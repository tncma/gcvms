package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import com.cma.gcvms.domain.Municipality;
import com.cma.gcvms.repository.MunicipalityRepository;
import com.cma.gcvms.web.domain.support.GenericLazyDataModel;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Provide PrimeFaces {@link LazyDataModel} for {@link Municipality}
 */
@Named
@ConversationContextScoped
public class MunicipalityLazyDataModel extends GenericLazyDataModel<Municipality, Integer, MunicipalitySearchForm> {
    private static final long serialVersionUID = 1L;

    @Inject
    public MunicipalityLazyDataModel(MunicipalityRepository municipalityRepository, MunicipalityController municipalityController,
            MunicipalitySearchForm municipalitySearchForm, MunicipalityExcelExporter municipalityExcelExporter) {
        super(municipalityRepository, municipalityController, municipalitySearchForm, municipalityExcelExporter);
    }
}