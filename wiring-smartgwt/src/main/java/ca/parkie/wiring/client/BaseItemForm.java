/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.wiring.entity.ItemType;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract public class BaseItemForm extends ItemForm
{
    private List<FormItem> baseItems = new ArrayList<FormItem>();

    protected BaseItemForm(String dataSourceId, ItemType itemType)
    {
        super(dataSourceId);

        TextItem name = new TextItem(WebConstants.ITEM_NAME);
        TextItem type = new TextItem(WebConstants.ITEM_TYPE);
        type.setValue(itemType.toString());
        type.setDisabled(true);

        baseItems.add(name);
        baseItems.add(type);
    }

    protected void addFormItems(FormItem... items)
    {
        baseItems.addAll(Arrays.asList(items));
    }

    protected void createForm()
    {
        FormItem[] array = new FormItem[baseItems.size()];
        baseItems.toArray(array);
        setFields(array);
    }
}
