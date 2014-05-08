/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.Record;

import java.util.ArrayList;
import java.util.List;

public class ViewUpdateHandler
{
    private List<RecordViewer> views;

    public ViewUpdateHandler()
    {
        views = new ArrayList<RecordViewer>();
    }

    public static ViewUpdateHandler retrieveFromRequest(DSRequest request)
    {
        return (ViewUpdateHandler) request.getAttributeAsObject(Constants.ATTR_VIEWS);
    }

    public void addRecordViewer(RecordViewer viewer)
    {
        views.add(viewer);
    }

    public void removeRecordViewer(RecordViewer viewer)
    {
        views.remove(viewer);
    }

    public void updateViews(Record record)
    {
        for (RecordViewer rv : views)
            rv.displayRecord(record);
    }

    public void resetViews()
    {
        for (RecordViewer rv: views)
            rv.resetViewer();
    }

    public void attachToRequest(DSRequest request)
    {
        request.setAttribute(Constants.ATTR_VIEWS, this);
    }
}
