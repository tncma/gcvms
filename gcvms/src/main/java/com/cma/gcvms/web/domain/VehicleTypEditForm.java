package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import com.cma.gcvms.domain.VehicleTyp;
import com.cma.gcvms.repository.VehicleTypRepository;
import com.cma.gcvms.web.domain.support.GenericEditForm;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper/Controller to edit {@link VehicleTyp}.
 */
@Named
@ConversationContextScoped
public class VehicleTypEditForm extends GenericEditForm<VehicleTyp, Integer> {
    @Inject
    protected VehicleTypController vehicleTypController;

    @Inject
    public VehicleTypEditForm(VehicleTypRepository vehicleTypRepository) {
        super(vehicleTypRepository);
    }

    /**
     * The entity to edit/view.
     */
    public VehicleTyp getVehicleTyp() {
        return getEntity();
    }

    public String print() {
        return vehicleTypController.print(getVehicleTyp());
    }
}
