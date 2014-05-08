/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.wiring.entity.ItemType;
import ca.parkie.wiring.service.NamingConvention;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;

import java.util.LinkedHashMap;

public class SwitchForm extends BaseItemForm
{
    public SwitchForm()
    {
        this(SwitchDataSource.ID, ItemType.Switch);
        createForm();
    }

    protected SwitchForm(String dataSourceId, ItemType itemType)
    {
        super(dataSourceId, itemType);

        // TODO Localize
        LinkedHashMap<String, String> speedMap = new LinkedHashMap<String, String>();
        speedMap.put("100", "100 Mb");
        speedMap.put("1000", "1 Gb");
        speedMap.put("10000", "10 Gb");

        IntegerItem size = new IntegerItem(WebConstants.SW_SIZE, "Size (U)");

        IntegerItem portCount = new IntegerItem(WebConstants.NET_PORT_COUNT, "Port Count");

        SelectItem portSpeed = new SelectItem(WebConstants.SW_PORT_SPEED, "Port Speed");
        portSpeed.setValueMap(speedMap);
        portSpeed.setDefaultToFirstOption(true);

        IntegerItem uplinkCount = new IntegerItem(WebConstants.SW_UPLINK_COUNT, "Uplink Count");

        SelectItem uplinkSpeed = new SelectItem(WebConstants.SW_UPLINK_SPEED, "Uplink Speed");
        uplinkSpeed.setValueMap(speedMap);
        uplinkSpeed.setDefaultToFirstOption(true);

        SelectItem naming = new SelectItem(WebConstants.SW_NAMING, "Port Naming");
        LinkedHashMap<String, String> namingMap = new LinkedHashMap<String, String>();
        namingMap.put(String.valueOf(NamingConvention.Cisco.toInt()), "Cisco");
        naming.setValueMap(namingMap);
        naming.setDefaultToFirstOption(true);

        addFormItems(size, portCount, portSpeed, uplinkCount, uplinkSpeed, naming);
    }
}
