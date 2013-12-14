package com.cma.gcvms.web.faces;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

import org.omnifaces.eventlistener.DefaultPhaseListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple phase listener logger. Convenient when learning/debugging JSF.
 * Must be activated in faces-config.xml
 */
public class LogPhaseListener extends DefaultPhaseListener {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(LogPhaseListener.class);

    public LogPhaseListener() {
        super(PhaseId.ANY_PHASE);
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        log.debug("{}", event.getPhaseId());
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        log.debug("{}", event.getPhaseId());
    }
}
