/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("M")
public class ManagedPort extends Port
{
    /*
    TODO Evaluate the usefulness of this.  It appears to be redundant due to Port's "device" instance field
    @ManyToOne
    @JoinColumn(name = "deviceId")
    private SwitchModule module;
    */

    @Column(name = "speed")
    private int speed;

    /*
    public SwitchModule getModule()
    {
        return module;
    }

    public void setModule(SwitchModule module)
    {
        this.module = module;
    }
    */

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }
}
