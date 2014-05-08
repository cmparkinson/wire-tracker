/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dao.impl;

import ca.parkie.wiring.dao.PatchPanelDao;
import ca.parkie.wiring.entity.PatchPanel;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPatchPanelDao extends AbstractJpaDao<PatchPanel> implements PatchPanelDao
{
    public JpaPatchPanelDao()
    {
        super(PatchPanel.class);
    }
}
