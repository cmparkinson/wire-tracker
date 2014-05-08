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
@Table(name = "floor")
public class Floor implements PersistentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "floorId")
    private int floorId;

    @OneToMany(mappedBy = "floor", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<Room>();

    @ManyToOne
    @JoinColumn(name = "buildingId", updatable = false, insertable = false)
    private Building building;

    @Version
    @Column(name = "version")
    private int version;

    @Column(name = "name")
    private String name;

    @Column(name = "drawingUri")
    private String drawingUri;

    public Building getBuilding()
    {
        return building;
    }

    public void setBuilding(Building building)
    {
        this.building = building;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDrawingUri()
    {
        return drawingUri;
    }

    public void setDrawingUri(String drawingUrl)
    {
        this.drawingUri = drawingUrl;
    }

    public void addRoom(Room room)
    {
        rooms.add(room);
    }

    public List<Room> getRooms()
    {
        return rooms;
    }

    public int getVersion()
    {
        return version;
    }

    public int getId()
    {
        return floorId;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }
}
