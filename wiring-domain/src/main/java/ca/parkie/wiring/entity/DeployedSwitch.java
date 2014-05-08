/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("W")
public class DeployedSwitch extends DeployedItem<Switch>
{
    @OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<DeployedSwitchModule> modules;

    @ManyToOne
    @JoinColumn(name = "itemId", updatable = false, insertable = false)
    private Switch item;

    public List<DeployedSwitchModule> getModules()
    {
        return modules;
    }

    public void setModules(List<DeployedSwitchModule> modules)
    {
        this.modules = modules;
    }

    @Override
    public Switch getItem()
    {
        return item;
    }

    @Override
    public void setItem(Switch item)
    {
        this.item = item;
    }
}
