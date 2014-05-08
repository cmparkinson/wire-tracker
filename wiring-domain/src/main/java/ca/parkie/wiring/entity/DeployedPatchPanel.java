/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("P")
public class DeployedPatchPanel extends DeployedNetworkDevice<PatchPanel>
{
    @Column(name = "pos1")
    private Integer positionInRack;

    @ManyToOne
    @JoinColumn(name = "itemId", updatable = false, insertable = false)
    private PatchPanel patchPanel;

    public Integer getPositionInRack()
    {
        return positionInRack;
    }

    public void setPositionInRack(Integer positionInRack)
    {
        this.positionInRack = positionInRack;
    }

    @Override
    public PatchPanel getItem()
    {
        return patchPanel;
    }

    @Override
    public void setItem(PatchPanel item)
    {
        patchPanel = item;
    }
}
