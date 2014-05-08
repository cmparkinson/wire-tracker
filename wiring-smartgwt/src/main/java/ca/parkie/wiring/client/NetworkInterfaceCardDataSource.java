/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;

public class NetworkInterfaceCardDataSource extends ItemDataSource
{
    public final static String ID = "nic";

    public NetworkInterfaceCardDataSource()
    {
        super(ID);
        setID(ID);

        // TODO Localize
        DataSourceIntegerField portCount = new DataSourceIntegerField(WebConstants.NET_PORT_COUNT, "Port Count");
        addField(portCount);

        DataSourceIntegerField portSpeed = new DataSourceIntegerField(WebConstants.NIC_PORT_SPEED, "Port Speed");
        addField(portSpeed);

        DataSourceBooleanField alphaNamingScheme = new DataSourceBooleanField(WebConstants.NIC_ALPHA_NAMING);
        alphaNamingScheme.setHidden(true);
        addField(alphaNamingScheme);
    }
}
