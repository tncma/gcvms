package com.cma.gcvms.web.domain;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.cma.gcvms.domain.DailyTrip;
import com.cma.gcvms.domain.DailyTrip_;
import com.cma.gcvms.domain.Employee;
import com.cma.gcvms.domain.Route;
import com.cma.gcvms.domain.Vehicle;
import com.cma.gcvms.repository.DailyTripRepository;
import com.cma.gcvms.web.domain.support.GenericEditForm;
import com.cma.gcvms.web.domain.support.GenericToOneAssociation;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper/Controller to edit {@link DailyTrip}.
 */
@Named
@ConversationContextScoped
public class DailyTripEditForm extends GenericEditForm<DailyTrip, Integer> {
    @Inject
    protected DailyTripController dailyTripController;
    @Inject
    protected VehicleController vehicleController;
    protected GenericToOneAssociation<Vehicle, Integer> vehicle;
    @Inject
    protected RouteController routeController;
    protected GenericToOneAssociation<Route, Integer> route;
    @Inject
    protected EmployeeController employeeController;
    protected GenericToOneAssociation<Employee, Integer> supervisor;

    @Inject
    public DailyTripEditForm(DailyTripRepository dailyTripRepository, DailyTripGraphLoader dailyTripGraphLoader) {
        super(dailyTripRepository, dailyTripGraphLoader);
    }

    /**
     * The entity to edit/view.
     */
    public DailyTrip getDailyTrip() {
        return getEntity();
    }

    public String print() {
        return dailyTripController.print(getDailyTrip());
    }

    @PostConstruct
    void setupVehicleActions() {
        vehicle = new GenericToOneAssociation<Vehicle, Integer>(vehicleController, DailyTrip_.vehicle) {
            @Override
            protected Vehicle get() {
                return getDailyTrip().getVehicle();
            }

            @Override
            protected void set(Vehicle vehicle) {
                getDailyTrip().setVehicle(vehicle);
            }

            @NotNull
            @Override
            public Vehicle getSelected() {
                return super.getSelected();
            }
        };
    }

    public GenericToOneAssociation<Vehicle, Integer> getVehicle() {
        return vehicle;
    }

    @PostConstruct
    void setupRouteActions() {
        route = new GenericToOneAssociation<Route, Integer>(routeController, DailyTrip_.route) {
            @Override
            protected Route get() {
                return getDailyTrip().getRoute();
            }

            @Override
            protected void set(Route route) {
                getDailyTrip().setRoute(route);
            }

            @NotNull
            @Override
            public Route getSelected() {
                return super.getSelected();
            }
        };
    }

    public GenericToOneAssociation<Route, Integer> getRoute() {
        return route;
    }

    @PostConstruct
    void setupSupervisorActions() {
        supervisor = new GenericToOneAssociation<Employee, Integer>(employeeController, DailyTrip_.supervisor) {
            @Override
            protected Employee get() {
                return getDailyTrip().getSupervisor();
            }

            @Override
            protected void set(Employee employee) {
                getDailyTrip().setSupervisor(employee);
            }
        };
    }

    public GenericToOneAssociation<Employee, Integer> getSupervisor() {
        return supervisor;
    }
}
