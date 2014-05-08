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
@Table(name = "building")
public class Building implements PersistentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buildingId")
    private int buildingId;

    @ManyToOne
    @JoinColumn(name = "campusId", updatable = false, insertable = false)
    private Campus campus;

    @OneToMany(mappedBy = "building", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Floor> floors = new ArrayList<Floor>();

    @Column(name = "name")
    private String name;

    @Version
    @Column(name = "version")
    private int version;

    public int getBuildingId()
    {
        return buildingId;
    }

    public void setBuildingId(int buildingId)
    {
        this.buildingId = buildingId;
    }

    public Campus getCampus()
    {
        return campus;
    }

    public void setCampus(Campus campus)
    {
        this.campus = campus;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void addFloor(Floor floor)
    {
        floors.add(floor);
    }

    public List<Floor> getFloors()
    {
        return floors;
    }

    public int getVersion()
    {
        return version;
    }

    public int getId()
    {
        return buildingId;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }
}
