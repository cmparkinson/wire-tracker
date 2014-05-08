/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("M")
public class DeployedSwitchModule extends DeployedNetworkDevice<SwitchModule>
{
    @Column(name = "pos1")
    private int moduleNumber;

    @ManyToOne
    @JoinColumn(name = "itemId", updatable = false, insertable = false)
    private SwitchModule switchModule;

    public int getModuleNumber()
    {
        return moduleNumber;
    }

    public void setModuleNumber(int moduleNumber)
    {
        this.moduleNumber = moduleNumber;
    }

    @Override
    public SwitchModule getItem()
    {
        return switchModule;
    }

    @Override
    public void setItem(SwitchModule item)
    {
        switchModule = item;
    }
}
