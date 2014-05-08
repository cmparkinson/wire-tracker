/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.smartgwt.client.data.DataSource;

public class DataSourceFactory
{
    public static DataSource get(String id)
    {
        DataSource ds = DataSource.get(id);
        if (ds != null)
            return ds;

        if (id.equals(ItemDataSource.ID))
            return new ItemDataSource();

        if (id.equals(RackDataSource.ID))
            return new RackDataSource();

        return null;
    }
}
