package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import com.cma.gcvms.domain.DailyTrip;
import com.cma.gcvms.repository.DailyTripRepository;
import com.cma.gcvms.web.domain.support.GenericLazyDataModel;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Provide PrimeFaces {@link LazyDataModel} for {@link DailyTrip}
 */
@Named
@ConversationContextScoped
public class DailyTripLazyDataModel extends GenericLazyDataModel<DailyTrip, Integer, DailyTripSearchForm> {
    private static final long serialVersionUID = 1L;

    @Inject
    public DailyTripLazyDataModel(DailyTripRepository dailyTripRepository, DailyTripController dailyTripController, DailyTripSearchForm dailyTripSearchForm,
            DailyTripExcelExporter dailyTripExcelExporter) {
        super(dailyTripRepository, dailyTripController, dailyTripSearchForm, dailyTripExcelExporter);
    }
}