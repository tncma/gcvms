package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import com.cma.gcvms.domain.TripSchedule;
import com.cma.gcvms.repository.TripScheduleRepository;
import com.cma.gcvms.web.domain.support.GenericLazyDataModel;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Provide PrimeFaces {@link LazyDataModel} for {@link TripSchedule}
 */
@Named
@ConversationContextScoped
public class TripScheduleLazyDataModel extends GenericLazyDataModel<TripSchedule, Integer, TripScheduleSearchForm> {
    private static final long serialVersionUID = 1L;

    @Inject
    public TripScheduleLazyDataModel(TripScheduleRepository tripScheduleRepository, TripScheduleController tripScheduleController,
            TripScheduleSearchForm tripScheduleSearchForm, TripScheduleExcelExporter tripScheduleExcelExporter) {
        super(tripScheduleRepository, tripScheduleController, tripScheduleSearchForm, tripScheduleExcelExporter);
    }
}