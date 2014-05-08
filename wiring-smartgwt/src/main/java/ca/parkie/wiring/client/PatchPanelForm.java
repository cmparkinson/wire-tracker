/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.wiring.entity.ItemType;
import com.smartgwt.client.widgets.form.fields.IntegerItem;

public class PatchPanelForm extends BaseItemForm
{
    public PatchPanelForm()
    {
        this(PatchPanelDataSource.ID, ItemType.PatchPanel);
        createForm();
    }

    protected PatchPanelForm(String dataSourceId, ItemType itemType)
    {
        super(dataSourceId, itemType);

        // TODO Localize
        IntegerItem portCount = new IntegerItem(WebConstants.NET_PORT_COUNT, "Port Count");
        IntegerItem size = new IntegerItem(WebConstants.PATCH_PANEL_SIZE, "Size (U)");
        
        addFormItems(portCount, size);
    }
}
