/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.controller;

import ca.parkie.wiring.client.JSOConstants;
import ca.parkie.wiring.dto.client.*;
import ca.parkie.wiring.dto.server.*;
import ca.parkie.wiring.service.Service;
import com.smartgwt.client.rpc.RPCResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FetchRestController extends AbstractRestController
{
    @RequestMapping("/fetch/server")
    @ResponseBody
    public Map<String, Object> listServers(@RequestBody ClientServerFetchDTO dto)
    {
        Map<String, Object> data = buildList(getServerService(), dto, ServerDTO.class);
        return buildResponse(data);
    }
    
    @RequestMapping("/fetch/patch")
    @ResponseBody
    public Map<String, Object> listPatchPanels(@RequestBody ClientPatchPanelFetchDTO dto)
    {
        Map<String, Object> data = buildList(getPatchPanelService(), dto, PatchPanelDTO.class);
        return buildResponse(data);
    }

    @RequestMapping("/fetch/nic")
    @ResponseBody
    public Map<String, Object> listNICs(@RequestBody ClientNICFetchDTO dto)
    {
        Map<String, Object> data = buildList(getNetworkInterfaceCardService(), dto, NetworkInterfaceCardDTO.class);
        return buildResponse(data);
    }

    @RequestMapping("/fetch/rack")
    @ResponseBody
    public Map<String, Object> listRacks(@RequestBody ClientRackFetchDTO dto)
    {
        Map<String, Object> data = buildList(getRackService(), dto, RackDTO.class);
        return buildResponse(data);
    }

    @RequestMapping("/fetch/switch")
    @ResponseBody
    public Map<String, Object> listSwitches(@RequestBody ClientSwitchFetchDTO dto)
    {
        Map<String, Object> data = buildList(getSwitchService(), dto, SwitchDTO.class);
        return buildResponse(data);
    }

    @RequestMapping("/fetch/item")
    @ResponseBody
    public Map<String, Object> listItems(@RequestBody ClientItemFetchDTO dto)
    {
        Map<String, Object> raw = dto.getData();
        raw.put("anonymous", false);

        Map<String, Object> data = buildList(getItemService(), dto, ItemDTO.class);
        return buildResponse(data);
    }

    private Map<String, Object> buildResponse(Map<String, Object> data)
    {
        Map<String, Object> response = new HashMap<String, Object>();

        response.put(JSOConstants.RESPONSE, data);
        return response;
    }

    private <E, D> Map<String, Object> buildList(Service<E> service, ClientFetchDTO<D> clientDto, Class<D> dtoClass)
    {
        try
        {
            List<E> entityList = service.getEntityList(
                    clientDto.getData(),
                    clientDto.getOrderBy(),
                    clientDto.getStartRow(),
                    clientDto.getTotalRows());

            List<D> dtoList = new ArrayList<D>();

            for (E e : entityList)
            {
                D dto = getDozerMapper().map(e, dtoClass);
                dtoList.add(dto);
            }

            int size = dtoList.size();
            int endRow = clientDto.getStartRow() + size;

            Map<String, Object> map = new HashMap<String, Object>();
            map.put(JSOConstants.STATUS, RPCResponse.STATUS_SUCCESS);
            map.put(JSOConstants.TOTAL_ROWS, size);
            map.put(JSOConstants.START_ROW, clientDto.getStartRow());
            map.put(JSOConstants.END_ROW, endRow);
            map.put(JSOConstants.DATA, dtoList);

            return map;
        }
        catch (Exception e)
        {
            // TODO Log properly
            e.printStackTrace();
            return buildErrorResponse(e.getMessage());
        }
    }

    private Map<String, Object> buildErrorResponse(String message)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(JSOConstants.STATUS, RPCResponse.STATUS_FAILURE);
        map.put(JSOConstants.DATA, message);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put(JSOConstants.RESPONSE, map);
        return response;
    }
}
