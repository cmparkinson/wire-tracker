/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.wiring.entity.ItemType;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class ItemDataSource extends AbstractRestDataSource
{
    public final static String ID = "item";

    protected ItemDataSource(String url)
    {
        super(url);

        DataSourceIntegerField itemId = new DataSourceIntegerField(JSOConstants.ITEM_ID);
        itemId.setPrimaryKey(true);
        itemId.setHidden(true);

        DataSourceTextField name = new DataSourceTextField(WebConstants.ITEM_NAME);

        DataSourceTextField type = new DataSourceTextField(WebConstants.ITEM_TYPE);
        ItemType[] typeArray = ItemType.values();
        String[] sArray = new String[typeArray.length];

        for (int i = 0; i < typeArray.length; i++)
            sArray[i] = typeArray[i].toString();

        type.setValueMap(sArray);

        DataSourceIntegerField version = new DataSourceIntegerField("version");
        version.setHidden(true);

        setFields(itemId, name, type, version);
    }

    public ItemDataSource()
    {
        this("item");
        setID(ID);
    }
}
