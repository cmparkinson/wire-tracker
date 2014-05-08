/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("N")
public class NetworkInterfaceCard extends NetworkDevice
{
    @OneToMany(mappedBy = "device", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("name")
    private List<ManagedPort> ports;

    @Override
    public List<ManagedPort> getPorts()
    {
        return ports;
    }

    public int getPortSpeed()
    {
        // NICs should never be created with zero ports, so there's no chance of a NPE
        ManagedPort port = ports.get(0);
        return port.getSpeed();
    }

    public void setPorts(List<ManagedPort> ports)
    {
        this.ports = ports;
    }

    @Override
    public ItemType getItemType()
    {
        return ItemType.NIC;
    }
}
