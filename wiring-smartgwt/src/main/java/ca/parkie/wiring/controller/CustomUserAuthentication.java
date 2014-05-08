/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserAuthentication implements Authentication
{
    private boolean authenticated = false;

    private GrantedAuthority grantedAuthority;
    private Authentication authentication;

    public CustomUserAuthentication(String role, Authentication authentication)
    {
        grantedAuthority = new GrantedAuthorityImpl(role);
        this.authentication = authentication;
    }

    public Collection<GrantedAuthority> getAuthorities()
    {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(grantedAuthority);
        return authorities;
    }

    public Object getCredentials()
    {
        return authentication.getCredentials();
    }

    public Object getDetails()
    {
        return authentication.getDetails();
    }

    public Object getPrincipal()
    {
        return authentication.getPrincipal();
    }

    public boolean isAuthenticated()
    {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException
    {
        this.authenticated = authenticated;
    }

    public String getName()
    {
        return getClass().getSimpleName();
    }
}
