package com.cma.gcvms.web.util;

import javax.faces.application.FacesMessage;

/**
 * Integration _HACK_ : would be nice if jsf was providing the id...
 * Wrap {@link FacesMessage} along with the id of the associated component.
 */
public class Message {

    private String sourceId;
    private FacesMessage facesMessage;

    public Message(String sourceId, FacesMessage facesMessage) {
        this.sourceId = sourceId;
        this.facesMessage = facesMessage;
    }

    public String getSourceId() {
        return sourceId;
    }

    public FacesMessage getFacesMessage() {
        return facesMessage;
    }

    public String getSeverity() {
        return MessageUtil.toCssFriendly(facesMessage.getSeverity());
    }
}
