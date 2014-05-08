/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;

@Entity
@Table(name = "port")
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("P")
public class Port implements Comparable<Port>, PersistentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portId")
    private int portId;

    @ManyToOne
    @JoinColumn(name = "itemId")
    protected NetworkDevice device;

    @Column(name = "name")
    protected String name;

    @Column(name = "abbrev")
    protected String abbrev;

    @Version
    @Column(name = "version")
    private int version;

    public int compareTo(Port port)
    {
        return name.compareTo(port.name);
    }

    public int getVersion()
    {
        return version;
    }

    public int getId()
    {
        return portId;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public NetworkDevice getDevice()
    {
        return device;
    }

    public void setDevice(NetworkDevice device)
    {
        this.device = device;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAbbrev()
    {
        return abbrev;
    }

    public void setAbbrev(String abbrev)
    {
        this.abbrev = abbrev;
    }
}
