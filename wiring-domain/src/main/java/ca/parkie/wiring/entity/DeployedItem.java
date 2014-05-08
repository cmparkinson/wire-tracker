/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;

@Entity
@Table(name = "deployedItem")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.CHAR)
abstract public class DeployedItem<T extends Item> implements PersistentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deployedId")
    private int deployedId;

    @ManyToOne
    @JoinColumn(name = "parentId", updatable = false, insertable = false)
    protected DeployedItem parent;

    @ManyToOne
    @JoinColumn(name = "roomId", updatable = false, insertable = false)
    private Room room;

    @Column(name = "name")
    private String name;

    @Version
    @Column(name = "version")
    private int version;

    abstract public T getItem();
    abstract public void setItem(T item);

    public DeployedItem getParent()
    {
        return parent;
    }

    public void setParent(DeployedItem parent)
    {
        this.parent = parent;
    }

    public int getVersion()
    {
        return version;
    }

    public int getId()
    {
        return deployedId;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public Room getRoom()
    {
        return room;
    }

    public void setRoom(Room room)
    {
        this.room = room;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
