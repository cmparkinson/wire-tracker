/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;
import java.util.List;


@Entity
abstract public class NetworkDevice extends Item
{
    abstract public List<? extends Port> getPorts();

    public int getPortCount()
    {
        return getPorts().size();
    }
}
