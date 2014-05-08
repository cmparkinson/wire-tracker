/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("R")
public class Rack extends Item
{
    @Column(name = "size")
    private int size;

    @Override
    public ItemType getItemType()
    {
        return ItemType.Rack;
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
