/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service;

public interface SwitchPortNameGenerator
{
    SwitchPortName getNext();

    void reset();

    void setPortSpeed(int portSpeed);

    void setModuleNumber(int moduleNumber);

    void setZeroBased(boolean zeroBased);

    void setSwitchNumber(int switchNumber);

    void setStartPort(int portNumber);
}
