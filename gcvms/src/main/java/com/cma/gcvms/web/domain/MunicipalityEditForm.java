package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;

import com.cma.gcvms.domain.Municipality;
import com.cma.gcvms.repository.MunicipalityRepository;
import com.cma.gcvms.web.domain.support.GenericEditForm;
import com.cma.gcvms.web.faces.ConversationContextScoped;

/**
 * View Helper/Controller to edit {@link Municipality}.
 */
@Named
@ConversationContextScoped
public class MunicipalityEditForm extends GenericEditForm<Municipality, Integer> {
    @Inject
    protected MunicipalityController municipalityController;

    @Inject
    public MunicipalityEditForm(MunicipalityRepository municipalityRepository) {
        super(municipalityRepository);
    }

    /**
     * The entity to edit/view.
     */
    public Municipality getMunicipality() {
        return getEntity();
    }

    public String print() {
        return municipalityController.print(getMunicipality());
    }
}
