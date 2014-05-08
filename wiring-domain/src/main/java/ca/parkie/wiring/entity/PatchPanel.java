/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("P")
public class PatchPanel extends NetworkDevice
{
    @OneToMany(mappedBy = "device", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("name")
    private List<Port> ports;

    @Column(name = "size")
    private int size;

    @Override
    public List<? extends Port> getPorts()
    {
        return ports;
    }

    public void setPorts(List<Port> ports)
    {
        this.ports = ports;
    }

    @Override
    public ItemType getItemType()
    {
        return ItemType.PatchPanel;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }
}
