/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service;

import ca.parkie.wiring.entity.Switch;

public interface SwitchService extends Service<Switch>
{
    Switch configureSwitch(Switch sw, int portCount, int portSpeed, int uplinkCount, int uplinkSpeed, NamingConvention naming);
    Switch updateSwitch(Switch sw, int portCount, int portSpeed, int uplinkCount, int uplinkSpeed, NamingConvention naming);
}
