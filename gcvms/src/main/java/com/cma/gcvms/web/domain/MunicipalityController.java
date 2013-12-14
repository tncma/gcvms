package com.cma.gcvms.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.domain.Municipality;
import com.cma.gcvms.printer.MunicipalityPrinter;
import com.cma.gcvms.repository.MunicipalityRepository;
import com.cma.gcvms.web.domain.support.GenericController;
import com.cma.gcvms.web.permission.MunicipalityPermission;

/**
 * Stateless controller for {@link Municipality} conversation start.
 */
@Named
@Singleton
public class MunicipalityController extends GenericController<Municipality, Integer> {
    public static final String MUNICIPALITY_EDIT_URI = "/domain/municipalityEdit.faces";
    public static final String MUNICIPALITY_SELECT_URI = "/domain/municipalitySelect.faces";

    @Inject
    public MunicipalityController(MunicipalityRepository municipalityRepository, MunicipalityPermission municipalityPermission,
            MunicipalityPrinter municipalityPrinter) {
        super(municipalityRepository, municipalityPermission, municipalityPrinter, MUNICIPALITY_SELECT_URI, MUNICIPALITY_EDIT_URI);
    }
}