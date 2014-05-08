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
@DiscriminatorValue("W")
public class Switch extends Item
{
    @OneToMany(mappedBy = "parentSwitch")
    private List<SwitchModule> modules;

    @Column(name = "size")
    private int size;

    @Override
    public ItemType getItemType()
    {
        return ItemType.Switch;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public List<SwitchModule> getModules()
    {
        return modules;
    }

    public void setModules(List<SwitchModule> modules)
    {
        this.modules = modules;
    }
}
