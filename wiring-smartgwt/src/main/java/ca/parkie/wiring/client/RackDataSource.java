/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.smartgwt.client.data.fields.DataSourceIntegerField;

public class RackDataSource extends ItemDataSource
{
    public final static String ID = "rack";

    public RackDataSource()
    {
        super(ID);
        setID(ID);

        // TODO Localize
        DataSourceIntegerField size = new DataSourceIntegerField(WebConstants.RACK_SIZE, "Size (U)");
        addField(size);
    }
}
