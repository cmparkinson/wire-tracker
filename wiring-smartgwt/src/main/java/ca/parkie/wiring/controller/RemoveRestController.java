/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.controller;

import ca.parkie.wiring.client.JSOConstants;
import ca.parkie.wiring.dto.client.*;
import ca.parkie.wiring.dto.server.*;
import ca.parkie.wiring.entity.*;
import ca.parkie.wiring.service.Service;
import com.smartgwt.client.rpc.RPCResponse;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RemoveRestController extends AbstractRestController
{
    @RequestMapping("/remove/rack")
    @ResponseBody
    public Map<String, Object> removeRack(@RequestBody ClientRackPersistDTO dto)
    {
        Rack rack = dtoToEntity(dto, Rack.class);
        return removeObject(rack, RackDTO.class, null, getRackService());
    }

    @RequestMapping("/remove/item")
    @ResponseBody
    public Map<String, Object> removeItem(@RequestBody ClientItemPersistDTO dto)
    {
        Item item = dtoToEntity(dto, Item.class);
        return removeObject(item, ItemDTO.class, null, getItemService());
    }
    
    @RequestMapping("/remove/nic")
    @ResponseBody
    public Map<String, Object> removeNIC(@RequestBody ClientNICPersistDTO dto)
    {
        NetworkInterfaceCard nic = dtoToEntity(dto, NetworkInterfaceCard.class);
        return removeObject(nic, NetworkInterfaceCardDTO.class, null, getNetworkInterfaceCardService());
    }

    @RequestMapping("/remove/patch")
    @ResponseBody
    public Map<String, Object> removeNIC(@RequestBody ClientPatchPanelPersistDTO dto)
    {
        PatchPanel panel = dtoToEntity(dto, PatchPanel.class);
        return removeObject(panel, PatchPanelDTO.class, null, getPatchPanelService());
    }
    
    @RequestMapping("/remove/server")
    @ResponseBody
    public Map<String, Object> removeServer(@RequestBody ClientServerPersistDTO dto)
    {
        Server server = dtoToEntity(dto, Server.class);
        return removeObject(server, ServerDTO.class, null, getServerService());
    }

    @RequestMapping("/remove/switch")
    @ResponseBody
    public Map<String, Object> removeSwitch(@RequestBody ClientSwitchPersistDTO dto)
    {
        Switch sw = dtoToEntity(dto, Switch.class);
        return removeObject(sw, SwitchDTO.class, null, getSwitchService());
    }
}
