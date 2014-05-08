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
public class UpdateRestController extends AbstractRestController
{
    @RequestMapping("/update/rack")
    @ResponseBody
    public Map<String, Object> updateRack(@RequestBody ClientRackPersistDTO dto)
    {
        Rack rack = dtoToEntity(dto, Rack.class);
        return updateObject(rack, RackDTO.class, null, getRackService());
    }

    @RequestMapping("/update/nic")
    @ResponseBody
    public Map<String, Object> updateNIC(@RequestBody ClientNICPersistDTO dto)
    {
        // TODO Invoke a validator before saving

        NetworkInterfaceCardDTO entityDto = dto.getNewValues();
        NetworkInterfaceCard nic = getDozerMapper().map(entityDto, NetworkInterfaceCard.class);

        NetworkInterfaceCardService service = getNetworkInterfaceCardService();
        nic = service.updateNIC(nic, entityDto.getPortCount(), entityDto.getPortSpeed(), entityDto.isAlphaNamingScheme());

        return updateObject(nic, NetworkInterfaceCardDTO.class, null, service);
    }

    @RequestMapping("/update/patch")
    @ResponseBody
    public Map<String, Object> updatePatchPanel(@RequestBody ClientPatchPanelPersistDTO dto)
    {
        // TODO Invoke a validator before saving

        PatchPanelDTO entityDto = dto.getNewValues();
        PatchPanel panel = getDozerMapper().map(entityDto, PatchPanel.class);

        PatchPanelService service = getPatchPanelService();
        panel = service.updatePatchPanel(panel, entityDto.getPortCount());

        return updateObject(panel, PatchPanelDTO.class, null, service);
    }
    
    @RequestMapping("/update/server")
    @ResponseBody
    public Map<String, Object> updateServer(@RequestBody ClientServerPersistDTO dto)
    {
        Server server = dtoToEntity(dto, Server.class);
        return updateObject(server, ServerDTO.class, null, getServerService());
    }

    @RequestMapping("/update/switch")
    @ResponseBody
    public Map<String, Object> updateSwitch(@RequestBody ClientSwitchPersistDTO dto)
    {
        SwitchDTO entityDto = dto.getNewValues();
        Switch sw = getDozerMapper().map(entityDto, Switch.class);

        SwitchService service = getSwitchService();
        sw = service.updateSwitch(sw,
                entityDto.getPortCount(),
                entityDto.getPortSpeed(),
                entityDto.getUplinkCount(),
                entityDto.getUplinkSpeed(),
                NamingConvention.Cisco);

        return updateObject(sw, SwitchDTO.class, null, service);
    }
}
