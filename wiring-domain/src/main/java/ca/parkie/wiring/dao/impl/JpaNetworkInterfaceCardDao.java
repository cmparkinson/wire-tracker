/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dao.impl;

import ca.parkie.wiring.dao.NetworkInterfaceCardDao;
import ca.parkie.wiring.entity.NetworkInterfaceCard;
import org.springframework.stereotype.Repository;

@Repository
public class JpaNetworkInterfaceCardDao extends AbstractJpaDao<NetworkInterfaceCard> implements NetworkInterfaceCardDao
{
    public JpaNetworkInterfaceCardDao()
    {
        super(NetworkInterfaceCard.class);
    }
}
