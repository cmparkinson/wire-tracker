/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;

@Entity
@Table(name = "room")
public class Room implements PersistentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId")
    private int roomId;

    @Column(name = "name")
    private String name;

    @Version
    @Column(name = "version")
    private int version;

    @ManyToOne
    @JoinColumn(name = "floorId", updatable = false, insertable = false)
    private Floor floor;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getVersion()
    {
        return version;
    }

    public int getId()
    {
        return roomId;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public Floor getFloor()
    {
        return floor;
    }

    public void setFloor(Floor floor)
    {
        this.floor = floor;
    }
}
