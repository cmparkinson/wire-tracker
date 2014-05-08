/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class SwitchDataSource extends ItemDataSource
{
    public final static String ID = "switch";

    public SwitchDataSource()
    {
        super(ID);
        setID(ID);

        // TODO Localize
        DataSourceIntegerField size = new DataSourceIntegerField(WebConstants.SW_SIZE, "Size (U");
        DataSourceIntegerField portCount = new DataSourceIntegerField(WebConstants.NET_PORT_COUNT, "Port Count");
        DataSourceIntegerField portSpeed = new DataSourceIntegerField(WebConstants.SW_PORT_SPEED, "Port Speed");
        DataSourceIntegerField uplinkCount = new DataSourceIntegerField(WebConstants.SW_UPLINK_COUNT, "Uplink Count");
        DataSourceIntegerField uplinkSpeed = new DataSourceIntegerField(WebConstants.SW_UPLINK_SPEED, "Uplink Speed");
        DataSourceIntegerField naming = new DataSourceIntegerField(WebConstants.SW_NAMING, "Port Naming");

        addField(size);
        addField(portCount);
        addField(portSpeed);
        addField(uplinkCount);
        addField(uplinkSpeed);
        addField(naming);
    }
}
