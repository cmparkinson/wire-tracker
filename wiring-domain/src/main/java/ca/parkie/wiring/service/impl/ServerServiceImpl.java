/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service.impl;

import ca.parkie.wiring.dao.GenericDao;
import ca.parkie.wiring.dao.ServerDao;
import ca.parkie.wiring.entity.Server;
import ca.parkie.wiring.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerServiceImpl extends AbstractService<Server> implements ServerService
{
    @Autowired
    private ServerDao dao;
    
    @Override
    protected void copyEntity(Server source, Server destination)
    {
        destination.setName(source.getName());
        destination.setVersion(source.getVersion());
        destination.setSize(source.getSize());
    }

    @Override
    protected GenericDao<Server> getDao()
    {
        return dao;
    }
}
