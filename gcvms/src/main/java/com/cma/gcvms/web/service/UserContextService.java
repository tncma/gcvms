package com.cma.gcvms.web.service;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import com.cma.gcvms.context.UserContext;

/**
 * Simple pass over to access static 'userContext' from EL.
 * @see org.springframework.security.web.authentication.AnonymousAuthenticationFilter
 */
@Named
@Singleton
public class UserContextService {
    public String getUsername() {
        return UserContext.getUsername();
    }

    public boolean isAnonymousUser() {
        return UserContext.ANONYMOUS_USER.equalsIgnoreCase(getUsername());
    }

    public boolean isLoggedIn() {
        return !isAnonymousUser();
    }

    public List<String> getRoles() {
        return UserContext.getRoles();
    }

    public boolean hasRole(String role) {
        return UserContext.getRoles().contains(role);
    }
}
