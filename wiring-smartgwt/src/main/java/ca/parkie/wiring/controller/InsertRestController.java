/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.controller;

import ca.parkie.wiring.dto.client.*;
import ca.parkie.wiring.dto.server.*;
import ca.parkie.wiring.entity.*;
import ca.parkie.wiring.service.NamingConvention;
import ca.parkie.wiring.service.NetworkInterfaceCardService;
import ca.parkie.wiring.service.PatchPanelService;
import ca.parkie.wiring.service.SwitchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class InsertRestController extends AbstractRestController
{
    @RequestMapping("/add/rack")
    @ResponseBody
    public Map<String, Object> insertRack(@RequestBody ClientRackPersistDTO dto)
    {
        Rack rack = dtoToEntity(dto, Rack.class);
        return insertObject(rack, RackDTO.class, null, getRackService());
    }

    @RequestMapping("/add/nic")
    @ResponseBody
    public Map<String, Object> insertNIC(@RequestBody ClientNICPersistDTO dto)
    {
        NetworkInterfaceCardDTO entityDto = dto.getNewValues();
        NetworkInterfaceCard nic = getDozerMapper().map(entityDto, NetworkInterfaceCard.class);

        NetworkInterfaceCardService service = getNetworkInterfaceCardService();
        service.configureNIC(nic, entityDto.getPortCount(), entityDto.getPortSpeed(), entityDto.isAlphaNamingScheme());

        return insertObject(nic, NetworkInterfaceCardDTO.class, null, service);
    }

    @RequestMapping("/add/patch")
    @ResponseBody
    public Map<String, Object> insertPatchPanel(@RequestBody ClientPatchPanelPersistDTO dto)
    {
        PatchPanelDTO entityDto = dto.getNewValues();
        PatchPanel panel = getDozerMapper().map(entityDto, PatchPanel.class);

        PatchPanelService service = getPatchPanelService();
        service.configurePatchPanel(panel, entityDto.getPortCount());

        return insertObject(panel, PatchPanelDTO.class, null, service);
    }
    
    @RequestMapping("/add/server")
    @ResponseBody
    public Map<String, Object> insertServer(@RequestBody ClientServerPersistDTO dto)
    {
        Server server = dtoToEntity(dto, Server.class);
        return insertObject(server, ServerDTO.class, null, getServerService());
    }

    @RequestMapping("/add/switch")
    @ResponseBody
    public Map<String, Object> insertSwitch(@RequestBody ClientSwitchPersistDTO dto)
    {
        SwitchDTO entityDto = dto.getNewValues();
        Switch sw = dtoToEntity(dto, Switch.class);

        SwitchService service = getSwitchService();
        service.configureSwitch(sw,
                entityDto.getPortCount(),
                entityDto.getPortSpeed(),
                entityDto.getUplinkCount(),
                entityDto.getUplinkSpeed(),
                NamingConvention.Cisco);

        return insertObject(sw, SwitchDTO.class, null, getSwitchService());
    }
}


