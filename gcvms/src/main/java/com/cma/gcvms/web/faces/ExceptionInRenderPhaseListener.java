package com.cma.gcvms.web.faces;

import java.io.IOException;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

import org.omnifaces.eventlistener.DefaultPhaseListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cma.gcvms.web.conversation.ConversationManager;
import com.google.common.collect.Iterables;

/**
 * Phase listener to handle exception during RENDER_RESPONSE phase.<br>
 * In other phases, exceptions are handled by factory.exception-handler-factory in faces-config.xml.<br>
 * The exception handler defined in faces-config.xml is also used in this phase listener.<br>
 */
public class ExceptionInRenderPhaseListener extends DefaultPhaseListener {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(ExceptionInRenderPhaseListener.class);

    public ExceptionInRenderPhaseListener() {
        super(PhaseId.RENDER_RESPONSE);
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        ExceptionHandler exceptionHandler = event.getFacesContext().getExceptionHandler();
        if (Iterables.isEmpty(exceptionHandler.getUnhandledExceptionQueuedEvents())) {
            return;
        }

        try {
            new OptimisticLockExceptionHandler(exceptionHandler).handle();

            FacesContext faces = FacesContext.getCurrentInstance();
            String nextUrl = ConversationManager.getInstance().getCurrentConversation().nextUrl();

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String url = externalContext.getRequestContextPath() + nextUrl;
            faces.getExternalContext().redirect(url);
            faces.responseComplete();
        } catch (IOException e) {
            log.error("Failed to redirect to context page in render response phase", e);
        }
    }
}
