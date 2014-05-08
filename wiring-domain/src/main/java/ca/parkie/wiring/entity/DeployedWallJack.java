/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("J")
public class DeployedWallJack extends DeployedNetworkDevice<WallJack>
{
    @Column(name = "pos1")
    private Integer x;

    @Column(name = "pos2")
    private Integer y;

    @ManyToOne
    @JoinColumn(name = "itemId", updatable = false, insertable = false)
    private WallJack wallJack;

    public Integer getX()
    {
        return x;
    }

    public void setX(Integer x)
    {
        this.x = x;
    }

    public Integer getY()
    {
        return y;
    }

    public void setY(Integer y)
    {
        this.y = y;
    }

    @Override
    public WallJack getItem()
    {
        return wallJack;
    }

    @Override
    public void setItem(WallJack item)
    {
        wallJack = item;
    }
}
