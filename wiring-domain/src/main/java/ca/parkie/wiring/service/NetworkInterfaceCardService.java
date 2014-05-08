/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service;

import ca.parkie.wiring.entity.NetworkInterfaceCard;
import org.springframework.transaction.annotation.Transactional;

public interface NetworkInterfaceCardService extends Service<NetworkInterfaceCard>
{
    NetworkInterfaceCard configureNIC(NetworkInterfaceCard nic, int portCount, int portSpeed, boolean alphaNaming);
    NetworkInterfaceCard updateNIC(NetworkInterfaceCard nic, int portCount, int portSpeed, boolean alphaNaming);
}
