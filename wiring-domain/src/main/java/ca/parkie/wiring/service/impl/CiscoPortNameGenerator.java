/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service.impl;

import ca.parkie.wiring.service.SwitchPortName;
import ca.parkie.wiring.service.SwitchPortNameGenerator;

public class CiscoPortNameGenerator implements SwitchPortNameGenerator
{
    private int portSpeed;

    private int currentPort;

    private int switchNumber = -1;
    
    private int moduleNumber = -1;

    private String portPrefix;
    
    private String portAbbrevPrefix;
    
    private boolean zeroBased = false;

    public void reset()
    {
        currentPort = zeroBased ? 0 : 1;
        portPrefix = getPrefixFromSpeed(portSpeed);
        portAbbrevPrefix = getAbbrevPrefixFromSpeed(portSpeed);
    }

    private String getAbbrevPrefixFromSpeed(int speed)
    {
        // TODO Localize?
        switch (speed)
        {
            case 100:
                return "fa";
            case 1000:
                return "gi";
            case 10000:
                return "te";
        }

        throw new IllegalArgumentException("The speed argument is not supported: " + speed);
    }
    
    private String getPrefixFromSpeed(int speed)
    {
        // TODO Localize?
        switch (speed)
        {
            case 100:
                return "FastEthernet";
            case 1000:
                return "GigabitEthernet";
            case 10000:
                return "TenGigabitEthernet";
        }

        throw new IllegalArgumentException("The speed argument is not supported: " + speed);
    }
    
    private SwitchPortName buildPortName(int portNumber, String prefix, String abbrevPrefix)
    {
        StringBuilder sb = new StringBuilder();

        if (switchNumber > 0)
            sb.append(switchNumber).append('/');

        sb.append(moduleNumber).append('/').append(portNumber);
        
        SwitchPortNameImpl name = new SwitchPortNameImpl();
        name.name = prefix + sb.toString();
        name.abbrev = abbrevPrefix + sb.toString();

        return name;
    }

    public void setPortSpeed(int portSpeed)
    {
        this.portSpeed = portSpeed;
        reset();
    }

    public void setModuleNumber(int moduleNumber)
    {
        this.moduleNumber = moduleNumber;
    }

    public void setZeroBased(boolean zeroBased)
    {
        this.zeroBased = zeroBased;
    }

    public void setSwitchNumber(int switchNumber)
    {
        this.switchNumber = switchNumber;
    }

    public SwitchPortName getNext()
    {
        return buildPortName(currentPort++, portPrefix, portAbbrevPrefix);
    }

    public void setStartPort(int portNumber)
    {
        currentPort = portNumber - 1;
    }
}
