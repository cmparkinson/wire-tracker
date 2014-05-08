/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service.impl;

import ca.parkie.wiring.service.SequenceGenerator;

public class AlphaSequenceGenerator implements SequenceGenerator
{
    // TODO Find a way to localize this, or provide pluggable alternatives

    private char current;
    private char start;

    public AlphaSequenceGenerator()
    {
        this.start = 'A';
        reset();
    }

    public void reset()
    {
        this.current = start;
    }

    public String getNext()
    {
        if (current > 'Z')
            throw new IllegalStateException("Attempted to increment alpha sequence past the end point (>Z).");

        return Character.toString(current++);
    }

    public void setStart(String start)
    {
        this.start = start.charAt(0);
        reset();
    }
}
