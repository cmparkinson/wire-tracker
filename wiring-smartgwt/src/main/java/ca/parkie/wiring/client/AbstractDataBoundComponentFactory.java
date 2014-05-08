/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.wiring.entity.ItemType;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.DataBoundComponent;

abstract public class AbstractDataBoundComponentFactory
{
    protected static <T extends DataBoundComponent> T configureComponent(ItemType type, T component)
    {
        switch (type)
        {
            case Rack:
                return buildComponent(component, RackDataSource.ID, RackDataSource.class);
            case NIC:
                return buildComponent(component, NetworkInterfaceCardDataSource.ID, NetworkInterfaceCardDataSource.class);
            case PatchPanel:
                return buildComponent(component, PatchPanelDataSource.ID, PatchPanelDataSource.class);
            case Server:
                return buildComponent(component, ServerDataSource.ID, ServerDataSource.class);
            case Switch:
                return buildComponent(component, SwitchDataSource.ID, SwitchDataSource.class);
            default:
                throw new RuntimeException("Unexpected type encountered: " + type.toString());
        }
    }

    private static <T extends DataBoundComponent> T buildComponent(T component, String id, Class<? extends DataSource> clazz)
    {
        DataSource ds = loadDataSource(id, clazz);
        component.setDataSource(ds);
        return component;
    }

    private static DataSource loadDataSource(String id, Class<? extends DataSource> clazz)
    {
        DataSource ds = DataSource.get(id);
        if (ds == null)
        {
            try
            {
                ds = pseudoReflectiveFactory(clazz);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        return ds;
    }

    private static DataSource pseudoReflectiveFactory(Class<?> clazz)
    {
        if (clazz == RackDataSource.class)
            return new RackDataSource();

        if (clazz == NetworkInterfaceCardDataSource.class)
            return new NetworkInterfaceCardDataSource();

        if (clazz == PatchPanelDataSource.class)
            return new PatchPanelDataSource();

        if (clazz == ServerDataSource.class)
            return new ServerDataSource();

        if (clazz == SwitchDataSource.class)
            return new SwitchDataSource();

        return null;
    }
}
