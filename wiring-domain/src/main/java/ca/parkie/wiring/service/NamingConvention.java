/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service;

public enum NamingConvention
{
    Cisco(0);

    private int naming;

    NamingConvention(int naming)
    {
        this.naming = naming;
    }

    public int toInt()
    {
        return naming;
    }

    public static NamingConvention getNamingFromInt(int i)
    {
        for (NamingConvention nc : values())
            if (nc.toInt() == i)
                return nc;

        return null;
    }
}
