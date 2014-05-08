/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("S")
public class DeployedServer extends DeployedItem<Server>
{
    @Column(name = "pos1")
    private Integer positionInRack;

    @ManyToOne
    @JoinColumn(name = "itemId", updatable = false, insertable = false)
    private Server server;

    public Integer getPositionInRack()
    {
        return positionInRack;
    }

    public void setPositionInRack(Integer positionInRack)
    {
        this.positionInRack = positionInRack;
    }

    @Override
    public Server getItem()
    {
        return server;
    }

    @Override
    public void setItem(Server item)
    {
        server = item;
    }
}
