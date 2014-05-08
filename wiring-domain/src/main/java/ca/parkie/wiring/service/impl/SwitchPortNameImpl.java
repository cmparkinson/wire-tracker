/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service.impl;

import ca.parkie.wiring.service.SwitchPortName;

class SwitchPortNameImpl implements SwitchPortName
{
    String name;
    String abbrev;

    public String getName()
    {
        return name;
    }

    public String getAbbrev()
    {
        return abbrev;
    }
}
