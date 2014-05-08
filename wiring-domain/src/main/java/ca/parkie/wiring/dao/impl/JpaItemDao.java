/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dao.impl;

import ca.parkie.wiring.dao.ItemDao;
import ca.parkie.wiring.entity.Item;
import org.springframework.stereotype.Repository;


@Repository
public class JpaItemDao extends AbstractJpaDao<Item> implements ItemDao
{
    public JpaItemDao()
    {
        super(Item.class);
    }
}
