/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.wiring.entity.Item;
import ca.parkie.wiring.entity.ItemType;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class DetailViewerFactory extends AbstractDataBoundComponentFactory
{
    public static DetailViewer createViewer(ItemType type)
    {
        DetailViewer viewer = new DetailViewer();
        return configureComponent(type, viewer);
    }

    public static Class<? extends DetailViewer> getComponentClassFromType(ItemType type)
    {
        switch (type)
        {
            default:
                return DetailViewer.class;
        }
    }
}
