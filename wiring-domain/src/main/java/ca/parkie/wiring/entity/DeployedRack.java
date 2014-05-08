/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("R")
public class DeployedRack extends DeployedItem<Rack>
{
    @OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<DeployedItem> rackContents;

    @ManyToOne
    @JoinColumn(name = "itemId", updatable = false, insertable = false)
    private Rack rack;

    public List<DeployedItem> getRackContents()
    {
        return rackContents;
    }

    public void setRackContents(List<DeployedItem> rackContents)
    {
        this.rackContents = rackContents;
    }

    @Override
    public Rack getItem()
    {
        return rack;
    }

    @Override
    public void setItem(Rack item)
    {
        rack = item;
    }
}
