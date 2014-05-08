/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.CHAR)
public class Item implements PersistentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId")
    private int itemId;

    @Column(name = "name")
    private String name;

    @Transient
    public ItemType getItemType()
    {
        return ItemType.Item;
    }

    @Column(name = "anonymous")
    private boolean anonymous = false;

    @Version
    @Column(name = "version")
    private int version;

    public int getVersion()
    {
        return version;
    }

    public int getId()
    {
        return itemId;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isAnonymous()
    {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous)
    {
        this.anonymous = anonymous;
    }
}
