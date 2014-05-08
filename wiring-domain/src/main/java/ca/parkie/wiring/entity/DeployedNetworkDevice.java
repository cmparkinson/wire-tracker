/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
abstract public class DeployedNetworkDevice<T extends Item> extends DeployedItem<T>
{
    @OneToMany(mappedBy = "localDevice", orphanRemoval = true, cascade = CascadeType.ALL)
    protected List<ConnectedPort> connectedPorts;

    public List<ConnectedPort> getConnectedPorts()
    {
        return connectedPorts;
    }

    public void setConnectedPorts(List<ConnectedPort> connectedPorts)
    {
        this.connectedPorts = connectedPorts;
    }
}
