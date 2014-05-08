/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dto;

import ca.parkie.wiring.dto.server.SwitchDTO;
import ca.parkie.wiring.entity.ItemType;
import ca.parkie.wiring.entity.Switch;
import ca.parkie.wiring.entity.SwitchModule;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

public class SwitchConverter implements CustomConverter
{
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass)
    {
        if (source == null)
            return null;

        if (sourceClass != Switch.class)
            throw new MappingException(SwitchConverter.class.getName() + " used incorrectly.  Source object is not type "
                    + Switch.class.getName());

        if (destinationClass != SwitchDTO.class)
            throw new MappingException(SwitchConverter.class.getName() + " used incorrectly.  Destination object is not type "
                    + SwitchDTO.class.getName());

        Switch sw = (Switch) source;

        SwitchDTO dto;
        if (destination == null)
            dto = new SwitchDTO();
        else
            dto = (SwitchDTO) destination;

        dto.setName(sw.getName());
        dto.setType(ItemType.Switch.toString());
        dto.setSize(sw.getSize());

        int portCount = 0;
        int uplinkCount = 0;
        int portSpeed = 0;
        int uplinkSpeed = 0;

        for (SwitchModule sm : sw.getModules())
        {
            if (sm.isUplink())
            {
                uplinkCount = sm.getPortCount();
                uplinkSpeed = sm.getPorts().get(0).getSpeed();
            }
            else
            {
                portCount = sm.getPortCount();
                portSpeed = sm.getPorts().get(0).getSpeed();
            }
        }

        dto.setPortCount(portCount);
        dto.setPortSpeed(portSpeed);
        dto.setUplinkCount(uplinkCount);
        dto.setUplinkSpeed(uplinkSpeed);

        return dto;
    }
}
