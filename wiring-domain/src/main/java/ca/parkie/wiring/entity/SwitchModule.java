/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("M")
public class SwitchModule extends NetworkDevice
{
    @OneToMany(mappedBy = "device", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("name")
    private List<ManagedPort> ports = new ArrayList<ManagedPort>();

    /* This property is used for standalone switches, not for deployed switch modules.
     */
    @ManyToOne
    @JoinColumn(name = "parentId")
    private Switch parentSwitch;

    @Column(name = "uplink")
    private boolean uplink;

    public void setUplink(boolean uplink)
    {
        this.uplink = uplink;
    }

    public boolean isUplink()
    {
        return uplink;
    }

    @Override
    public List<ManagedPort> getPorts()
    {
        return ports;
    }

    public void setPorts(List<ManagedPort> ports)
    {
        this.ports = ports;
    }

    public Switch getParentSwitch()
    {
        return parentSwitch;
    }

    public void setParentSwitch(Switch parentSwitch)
    {
        this.parentSwitch = parentSwitch;
        setAnonymous(true);
        // TODO Localize
        setName("anonymous");
    }

    @Override
    public ItemType getItemType()
    {
        return ItemType.SwitchModule;
    }
}
