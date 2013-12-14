package com.cma.gcvms.web.domain;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.cma.gcvms.domain.Vehicle;
import com.cma.gcvms.domain.VehicleTyp;
import com.cma.gcvms.domain.Vehicle_;
import com.cma.gcvms.repository.VehicleRepository;
import com.cma.gcvms.web.domain.support.GenericEditForm;
import com.cma.gcvms.web.domain.support.GenericToOneAssociation;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper/Controller to edit {@link Vehicle}.
 */
@Named
@ConversationContextScoped
public class VehicleEditForm extends GenericEditForm<Vehicle, Integer> {
    @Inject
    protected VehicleController vehicleController;
    @Inject
    protected VehicleTypController vehicleTypController;
    protected GenericToOneAssociation<VehicleTyp, Integer> typ;

    @Inject
    public VehicleEditForm(VehicleRepository vehicleRepository, VehicleGraphLoader vehicleGraphLoader) {
        super(vehicleRepository, vehicleGraphLoader);
    }

    /**
     * The entity to edit/view.
     */
    public Vehicle getVehicle() {
        return getEntity();
    }

    public String print() {
        return vehicleController.print(getVehicle());
    }

    @PostConstruct
    void setupTypActions() {
        typ = new GenericToOneAssociation<VehicleTyp, Integer>(vehicleTypController, Vehicle_.typ) {
            @Override
            protected VehicleTyp get() {
                return getVehicle().getTyp();
            }

            @Override
            protected void set(VehicleTyp vehicleTyp) {
                getVehicle().setTyp(vehicleTyp);
            }

            @NotNull
            @Override
            public VehicleTyp getSelected() {
                return super.getSelected();
            }
        };
    }

    public GenericToOneAssociation<VehicleTyp, Integer> getTyp() {
        return typ;
    }
}
