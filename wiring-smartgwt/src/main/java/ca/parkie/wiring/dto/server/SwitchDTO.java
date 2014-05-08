/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dto.server;

public class SwitchDTO extends ItemDTO
{
    private int portSpeed;

    private int portCount;

    private int uplinkSpeed;

    private int uplinkCount;

    private int size;

    private int naming;

    public int getPortSpeed()
    {
        return portSpeed;
    }

    public void setPortSpeed(int portSpeed)
    {
        this.portSpeed = portSpeed;
    }

    public int getPortCount()
    {
        return portCount;
    }

    public void setPortCount(int portCount)
    {
        this.portCount = portCount;
    }

    public int getUplinkSpeed()
    {
        return uplinkSpeed;
    }

    public void setUplinkSpeed(int uplinkSpeed)
    {
        this.uplinkSpeed = uplinkSpeed;
    }

    public int getUplinkCount()
    {
        return uplinkCount;
    }

    public void setUplinkCount(int uplinkCount)
    {
        this.uplinkCount = uplinkCount;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getNaming()
    {
        return naming;
    }

    public void setNaming(int naming)
    {
        this.naming = naming;
    }
}
