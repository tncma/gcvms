package com.cma.gcvms.audit;

import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;

/**
 * Execute an {@link AuditCallback} to enable/disable audit in {@link PreUpdate} and {@link PostUpdate} actions and/or force username to be used.
 * The {@link AuditTemplate} will be in charge of cleaning up the {@link AuditContextHolder} state.
 */
public class AuditTemplate {
    public interface AuditCallback<T> {
        T doInAudit() throws Exception;
    }

    final boolean auditing;
    final String username;

    /**
     * Enable or not audit
     */
    public AuditTemplate(boolean auditing) {
        this(auditing, null);
    }

    /**
     * Enable audit, and specify a username to be used
     */
    public AuditTemplate(String username) {
        this(true, username);
    }

    private AuditTemplate(boolean auditing, String username) {
        this.auditing = auditing;
        this.username = username;
    }

    public <T> T execute(AuditCallback<T> callback) throws Exception {
        AuditContextHolder.setAudit(auditing);
        AuditContextHolder.setUsername(username);
        try {
            return callback.doInAudit();
        } finally {
            AuditContextHolder.clear();
        }
    }
}