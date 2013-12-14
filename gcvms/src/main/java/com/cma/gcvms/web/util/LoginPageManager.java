package com.cma.gcvms.web.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omnifaces.util.Faces;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

/**
 * Manages the login page, implementing Spring Security login page with JSF
 */
@Named
public class LoginPageManager implements ApplicationEventPublisherAware {

    @Inject
    private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

    @Inject
    private SessionAuthenticationStrategy sessionAuthenticationStrategy;

    @Inject
    private SecurityContextRepository securityContextRepository;

    @Inject
    private MessageUtil messageUtil;

    private ApplicationEventPublisher applicationEventPublisher;

    public void setSessionExpired(String value) {
        messageUtil.warning("session_expired");
    }

    public String getSessionExpired() {
        return null;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void doLogin() {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest();
        HttpServletResponse response = (HttpServletResponse) ctx.getResponse();
        HttpRequestResponseHolder holder = new HttpRequestResponseHolder(request, response);
        SecurityContext securityContext = securityContextRepository.loadContext(holder);
        SecurityContextHolder.setContext(securityContext);
        try {
            Authentication authResult = usernamePasswordAuthenticationFilter.attemptAuthentication(request, response);
            if (authResult == null) {
                messageUtil.error("security_error");
                return;
            }
            sessionAuthenticationStrategy.onAuthentication(authResult, request, response);
            // below : do the same thing as in AbstractAuthenticationProcessingFilter.successfulAuthentication(),
            // except for the redirection to the login success URL that is managed by JSF
            securityContext.setAuthentication(authResult);
            usernamePasswordAuthenticationFilter.getRememberMeServices().loginSuccess(request, response, authResult);
            if (applicationEventPublisher != null) {
                applicationEventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, getClass()));
            }
            // redirects to the home page
            Faces.navigate("/home.faces?faces-redirect=true");
        } catch (UsernameNotFoundException e) {
            messageUtil.error("security_username_not_found");
        } catch (DisabledException e) {
            messageUtil.error("security_account_disabled");
        } catch (LockedException e) {
            messageUtil.error("security_account_locked");
        } catch (AccountExpiredException e) {
            messageUtil.error("security_account_expired");
        } catch (CredentialsExpiredException e) {
            messageUtil.error("security_account_credentials_expired");
        } catch (AccountStatusException e) {
            messageUtil.error("security_account_status_invalid");
        } catch (BadCredentialsException e) {
            messageUtil.error("security_bad_credentials");
        } catch (AuthenticationException e) {
            messageUtil.error("security_error");
        } finally {
            securityContextRepository.saveContext(securityContext, holder.getRequest(), holder.getResponse());
        }
    }
}
