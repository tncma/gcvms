package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import com.cma.gcvms.domain.Route;
import com.cma.gcvms.repository.RouteRepository;
import com.cma.gcvms.web.domain.support.GenericEditForm;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper/Controller to edit {@link Route}.
 */
@Named
@ConversationContextScoped
public class RouteEditForm extends GenericEditForm<Route, Integer> {
    @Inject
    protected RouteController routeController;

    @Inject
    public RouteEditForm(RouteRepository routeRepository) {
        super(routeRepository);
    }

    /**
     * The entity to edit/view.
     */
    public Route getRoute() {
        return getEntity();
    }

    public String print() {
        return routeController.print(getRoute());
    }
}
