/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.wiring.entity.ItemType;
import com.smartgwt.client.widgets.form.DynamicForm;

public class FormFactory extends AbstractDataBoundComponentFactory
{
    public static ItemForm createForm(ItemType type, ViewUpdateHandler viewUpdateHandler)
    {
        ItemForm form;
        switch (type)
        {
            case NIC:
                form = new NetworkInterfaceCardForm();
                break;
            case Rack:
                form = new RackForm();
                break;
            case PatchPanel:
                form = new PatchPanelForm();
                break;
            case Server:
                form = new ServerForm();
                break;
            case Switch:
                form = new SwitchForm();
                break;

            default:
                throw new RuntimeException("Unknown type encountered: " + type.toString());
        }

        if (viewUpdateHandler != null)
            form.setViewUpdateHandler(viewUpdateHandler);

        configureComponent(type, form);
        return form;
    }

    public static ItemForm createForm(ItemType type)
    {
        return createForm(type, null);
    }

    // TODO Remove
    public static Class<? extends DynamicForm> getComponentClassFromType(ItemType type)
    {
        switch (type)
        {
            case Rack:
                return RackForm.class;
            default:
                throw new RuntimeException("Unknown type encountered: " + type.toString());
        }
    }
}
