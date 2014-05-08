/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.wiring.entity.ItemType;
import com.smartgwt.client.widgets.form.fields.*;

public class RackForm extends BaseItemForm
{
    public RackForm()
    {
        this(RackDataSource.ID, ItemType.Rack);
        createForm();
    }

    protected RackForm(String dataSourceId, ItemType itemType)
    {
        super(dataSourceId, itemType);

        // TODO Localize
        IntegerItem size = new IntegerItem(WebConstants.RACK_SIZE, "Size (U)");
        addFormItems(size);
    }
}
