/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.smartgwt.client.data.fields.DataSourceIntegerField;

public class ServerDataSource extends ItemDataSource
{
    public final static String ID = "server";

    public ServerDataSource()
    {
        super(ID);
        setID(ID);

        // TODO Localize
        DataSourceIntegerField size = new DataSourceIntegerField(WebConstants.SERVER_SIZE, "Size (U)");
        addField(size);
    }
}
