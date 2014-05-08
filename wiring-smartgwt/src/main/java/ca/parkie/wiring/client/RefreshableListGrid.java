/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGrid;

// TODO Rename
public class RefreshableListGrid extends ListGrid implements RecordViewer
{
    public void displayRecord(Record record)
    {
        int index = getRecordIndex(record);

        // TODO Find some way to add the record without requiring a server round-trip.
        // New record, so invalidate the cache.
        if (index == -1)
        {
            invalidateCache();
            return;
        }

        Record existing = getRecord(index);

        // Copy the record, stripping out any component specific attributes
        Record copy = getDataSource().copyRecord(record);

        String pkField = getDataSource().getPrimaryKeyFieldName();

        for (String key : copy.getAttributes())
        {
            if (existing.getAttribute(key) != null && !key.equalsIgnoreCase(pkField))
                existing.setAttribute(key, copy.getAttribute(key));
        }

        refreshRow(index);
    }

    public void resetViewer()
    {
        invalidateCache();
    }
}
