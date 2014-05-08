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
@Table(name = "campus")
public class Campus implements PersistentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campusId")
    private int campusId;

    @Column(name = "name")
    private String name;

    @Version
    @Column(name = "version")
    private int version;

    @OneToMany(mappedBy = "campus", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Building> buildings = new ArrayList<Building>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void addBuilding(Building building)
    {
        buildings.add(building);
    }

    public List<Building> getBuildings()
    {
        return buildings;
    }

    public int getVersion()
    {
        return version;
    }

    public int getId()
    {
        return campusId;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }
}
