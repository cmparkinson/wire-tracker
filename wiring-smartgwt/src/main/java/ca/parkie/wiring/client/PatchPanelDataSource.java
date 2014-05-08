/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.smartgwt.client.data.fields.DataSourceIntegerField;

public class PatchPanelDataSource extends ItemDataSource
{
    public final static String ID = "patch";

    public PatchPanelDataSource()
    {
        super(ID);
        setID(ID);

        // TODO Localize
        DataSourceIntegerField portCount = new DataSourceIntegerField(WebConstants.NET_PORT_COUNT, "Port Count");
        DataSourceIntegerField size = new DataSourceIntegerField(WebConstants.PATCH_PANEL_SIZE, "Size (U)");
        
        addField(portCount);
        addField(size);
    }
}
