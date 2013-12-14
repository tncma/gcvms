package com.cma.gcvms.context;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Simple User that also keep track of the primary key.
 */
public class UserWithId extends User {
    private static final long serialVersionUID = 1L;
    private Serializable id;

    public UserWithId(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities, Serializable id) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    public Serializable getId() {
        return id;
    }
}