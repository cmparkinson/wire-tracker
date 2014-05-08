/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dao.impl;

import ca.parkie.wiring.dao.SwitchDao;
import ca.parkie.wiring.entity.Switch;
import org.springframework.stereotype.Repository;

@Repository
public class JpaSwitchDao extends AbstractJpaDao<Switch> implements SwitchDao
{
    public JpaSwitchDao()
    {
        super(Switch.class);
    }
}
