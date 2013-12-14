package com.cma.gcvms.web.faces;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

import org.omnifaces.eventlistener.DefaultPhaseListener;

import com.cma.gcvms.context.LocaleHolder;
import com.cma.gcvms.web.filter.LocaleResolverRequestFilter;

/**
 * Set the current locale to jsf from the resolver initialized in {@link LocaleResolverRequestFilter} filter.
 */
public class LocaleSetterListener extends DefaultPhaseListener {
    private static final long serialVersionUID = 1L;

    public LocaleSetterListener() {
        super(PhaseId.RESTORE_VIEW);
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        if (FacesContext.getCurrentInstance().getViewRoot() != null) {
            FacesContext.getCurrentInstance().getViewRoot().setLocale(LocaleHolder.getLocale());
        }
    }
}
