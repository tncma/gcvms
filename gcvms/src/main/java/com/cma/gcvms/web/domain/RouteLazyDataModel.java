package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import com.cma.gcvms.domain.Route;
import com.cma.gcvms.repository.RouteRepository;
import com.cma.gcvms.web.domain.support.GenericLazyDataModel;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * Provide PrimeFaces {@link LazyDataModel} for {@link Route}
 */
@Named
@ConversationContextScoped
public class RouteLazyDataModel extends GenericLazyDataModel<Route, Integer, RouteSearchForm> {
    private static final long serialVersionUID = 1L;

    @Inject
    public RouteLazyDataModel(RouteRepository routeRepository, RouteController routeController, RouteSearchForm routeSearchForm,
            RouteExcelExporter routeExcelExporter) {
        super(routeRepository, routeController, routeSearchForm, routeExcelExporter);
    }
}