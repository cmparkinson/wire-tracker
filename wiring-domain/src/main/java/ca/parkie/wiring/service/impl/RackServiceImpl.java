/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service.impl;

import ca.parkie.wiring.dao.GenericDao;
import ca.parkie.wiring.dao.RackDao;
import ca.parkie.wiring.entity.Rack;
import ca.parkie.wiring.service.RackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class RackServiceImpl extends AbstractService<Rack> implements RackService
{
    @Autowired
    private RackDao dao;

    @Override
    protected GenericDao<Rack> getDao()
    {
        return dao;
    }

    @Override
    protected void copyEntity(Rack source, Rack destination)
    {
        destination.setSize(source.getSize());
        destination.setName(source.getName());
        destination.setVersion(source.getVersion());
    }
}
