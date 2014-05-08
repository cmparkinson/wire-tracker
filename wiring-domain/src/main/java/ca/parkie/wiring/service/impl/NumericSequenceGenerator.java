/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service.impl;

import ca.parkie.wiring.service.SequenceGenerator;

public class NumericSequenceGenerator implements SequenceGenerator
{
    private int current;
    private int start;

    public NumericSequenceGenerator()
    {
        start = 0;
        reset();
    }

    public void reset()
    {
        current = start;
    }

    public String getNext()
    {
        return Integer.toString(current++);
    }

    public void setStart(String start)
    {
        this.start = Integer.parseInt(start);
    }
}
