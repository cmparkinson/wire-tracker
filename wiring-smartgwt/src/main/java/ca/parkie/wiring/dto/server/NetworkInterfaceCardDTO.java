/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dto.server;

public class NetworkInterfaceCardDTO extends NetworkDeviceDTO
{
    private int portSpeed;

    private boolean alphaNamingScheme;

    public int getPortSpeed()
    {
        return portSpeed;
    }

    public void setPortSpeed(int portSpeed)
    {
        this.portSpeed = portSpeed;
    }

    public boolean isAlphaNamingScheme()
    {
        return alphaNamingScheme;
    }

    public void setAlphaNamingScheme(boolean alphaNamingScheme)
    {
        this.alphaNamingScheme = alphaNamingScheme;
    }
}
