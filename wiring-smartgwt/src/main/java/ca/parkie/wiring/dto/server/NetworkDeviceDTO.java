/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dto.server;

abstract public class NetworkDeviceDTO extends ItemDTO
{
    private int portCount;

    public int getPortCount()
    {
        return portCount;
    }

    public void setPortCount(int portCount)
    {
        this.portCount = portCount;
    }
}
