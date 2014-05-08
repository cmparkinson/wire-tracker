/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import ca.parkie.webparam.WebParam;
import ca.parkie.wiring.dto.server.*;

public class WebConstants
{
    // Item parameters

    @WebParam(entity = ItemDTO.class)
    public final static String ITEM_NAME = "name";

    @WebParam(entity = ItemDTO.class)
    public final static String ITEM_TYPE = "type";

    @WebParam(entity = ItemDTO.class)
    public final static String ITEM_VERSION = "version";

    // Network Device Parameters

    @WebParam(entity = NetworkDeviceDTO.class)
    public final static String NET_PORT_COUNT = "portCount";

    // NIC Parameters

    @WebParam(entity = NetworkInterfaceCardDTO.class)
    public final static String NIC_ALPHA_NAMING = "alphaNamingScheme";

    @WebParam(entity = NetworkInterfaceCardDTO.class)
    public final static String NIC_PORT_SPEED = "portSpeed";

    // Rack Parameters

    @WebParam(entity = RackDTO.class)
    public final static String RACK_SIZE = "size";
    
    // Patch Panel Parameters
    
    @WebParam(entity = PatchPanelDTO.class)
    public final static String PATCH_PANEL_SIZE = "size";
    
    // Server Parameters
    
    @WebParam(entity = ServerDTO.class)
    public final static String SERVER_SIZE = "size";

    // Switch Parameters

    @WebParam(entity = SwitchDTO.class)
    public final static String SW_SIZE = "size";

    @WebParam(entity = SwitchDTO.class)
    public final static String SW_PORT_SPEED = "portSpeed";

    @WebParam(entity = SwitchDTO.class)
    public final static String SW_UPLINK_COUNT = "uplinkCount";

    @WebParam(entity = SwitchDTO.class)
    public final static String SW_UPLINK_SPEED = "uplinkSpeed";

    @WebParam(entity = SwitchDTO.class)
    public final static String SW_NAMING = "naming";
}
