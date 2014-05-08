/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;

@Entity
@Table(name = "connectedPort")
public class ConnectedPort implements PersistentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "connectionId")
    private int connectionId;

    @ManyToOne
    @JoinColumn(name = "deployedId", updatable = false, insertable = false)
    private DeployedItem localDevice;

    @ManyToOne
    @JoinColumn(name = "portId", updatable = false, insertable = false)
    private Port localPort;

    @ManyToOne
    @JoinColumn(name = "connectedDeployedId", updatable = false, insertable = false)
    private DeployedItem remoteDevice;

    @ManyToOne
    @JoinColumn(name = "connectedPortId", updatable = false, insertable = false)
    private Port remotePort;

    @Column(name = "cableLabel")
    private String cableLabel;

    @Version
    @Column(name = "version")
    private int version;

    public int getVersion()
    {
        return version;
    }

    public int getId()
    {
        return connectionId;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public DeployedItem getLocalDevice()
    {
        return localDevice;
    }

    public void setLocalDevice(DeployedItem localDevice)
    {
        this.localDevice = localDevice;
    }

    public Port getLocalPort()
    {
        return localPort;
    }

    public void setLocalPort(Port localPort)
    {
        this.localPort = localPort;
    }

    public DeployedItem getRemoteDevice()
    {
        return remoteDevice;
    }

    public void setRemoteDevice(DeployedItem remoteDevice)
    {
        this.remoteDevice = remoteDevice;
    }

    public Port getRemotePort()
    {
        return remotePort;
    }

    public void setRemotePort(Port remotePort)
    {
        this.remotePort = remotePort;
    }

    public String getCableLabel()
    {
        return cableLabel;
    }

    public void setCableLabel(String cableLabel)
    {
        this.cableLabel = cableLabel;
    }
}
