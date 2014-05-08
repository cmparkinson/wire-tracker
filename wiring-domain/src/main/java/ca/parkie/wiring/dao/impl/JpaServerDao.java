/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dao.impl;

import ca.parkie.wiring.dao.ServerDao;
import ca.parkie.wiring.entity.Server;
import org.springframework.stereotype.Repository;

@Repository
public class JpaServerDao extends AbstractJpaDao<Server> implements ServerDao
{
    public JpaServerDao()
    {
        super(Server.class);
    }
}
