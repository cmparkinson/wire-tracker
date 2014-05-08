/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service.impl;

import ca.parkie.wiring.dao.GenericDao;
import ca.parkie.wiring.dao.ItemDao;
import ca.parkie.wiring.entity.Item;
import ca.parkie.wiring.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends AbstractService<Item> implements ItemService
{
    @Autowired
    private ItemDao dao;

    @Override
    protected void copyEntity(Item source, Item destination)
    {
        destination.setName(source.getName());
        destination.setVersion(source.getVersion());
        destination.setAnonymous(source.isAnonymous());
    }

    @Override
    protected GenericDao<Item> getDao()
    {
        return dao;
    }
}
