/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.controller;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationProvider implements AuthenticationProvider
{
    // TODO Replace with real authentication
    private static Map<String, String> userMap = new HashMap<String, String>();
    static
    {
        userMap.put("test", "test123");
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        String storedPassword = userMap.get(username);
        if (storedPassword == null)
            throw new UsernameNotFoundException("User " + username + " not found.");

        if (!storedPassword.equals(password))
            throw new BadCredentialsException("Invalid password for user: " + username);

        Authentication auth = new CustomUserAuthentication("ROLE_USER", authentication);
        auth.setAuthenticated(true);
        return auth;
    }

    public boolean supports(Class<? extends Object> aClass)
    {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
