/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.wiring.entity.ItemType;
import com.smartgwt.client.widgets.form.fields.IntegerItem;

public class ServerForm extends BaseItemForm
{
    public ServerForm()
    {
        this(ServerDataSource.ID, ItemType.Server);
        createForm();
    }

    protected ServerForm(String dataSourceId, ItemType itemType)
    {
        super(dataSourceId, itemType);

        // TODO Localize
        IntegerItem size = new IntegerItem(WebConstants.SERVER_SIZE, "Size (U)");
        addFormItems(size);
    }
}
