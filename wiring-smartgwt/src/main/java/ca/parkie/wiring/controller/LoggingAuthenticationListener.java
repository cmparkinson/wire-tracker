/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.controller;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;

public class LoggingAuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent>
{
    public void onApplicationEvent(AbstractAuthenticationEvent abstractAuthenticationEvent)
    {
        // TODO Implement authentication logging
    }
}
