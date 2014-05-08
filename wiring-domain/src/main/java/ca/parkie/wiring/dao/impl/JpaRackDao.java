/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dao.impl;

import ca.parkie.wiring.dao.RackDao;
import ca.parkie.wiring.entity.Rack;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRackDao extends AbstractJpaDao<Rack> implements RackDao
{
    public JpaRackDao()
    {
        super(Rack.class);
    }
}
