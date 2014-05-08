/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.wiring.entity.ItemType;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;

import java.util.LinkedHashMap;

public class NetworkInterfaceCardForm extends BaseItemForm
{
    public NetworkInterfaceCardForm()
    {
        this(NetworkInterfaceCardDataSource.ID, ItemType.NIC);
        createForm();
    }

    protected NetworkInterfaceCardForm(String dataSourceId, ItemType itemType)
    {
        super(dataSourceId, itemType);

        // TODO Localize
        IntegerItem portCount = new IntegerItem(WebConstants.NET_PORT_COUNT, "Port Count");


        SelectItem portSpeed = new SelectItem(WebConstants.NIC_PORT_SPEED, "Port Speed");

        LinkedHashMap<String, String> speedMap = new LinkedHashMap<String, String>();
        speedMap.put("100", "100 Mb");
        speedMap.put("1000", "1 Gb");
        speedMap.put("10000", "10 Gb");

        portSpeed.setValueMap(speedMap);
        portSpeed.setDefaultToFirstOption(true);

        SelectItem namingScheme = new SelectItem(WebConstants.NIC_ALPHA_NAMING, "Naming Scheme");

        LinkedHashMap<String, String> namingSchemes = new LinkedHashMap<String, String>();
        namingSchemes.put("false", "Numeric");
        namingSchemes.put("true", "Alpha");

        namingScheme.setValueMap(namingSchemes);
        namingScheme.setDefaultToFirstOption(true);

        addFormItems(portCount, portSpeed, namingScheme);
    }
}
