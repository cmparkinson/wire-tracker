/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dto;

import ca.parkie.wiring.entity.ItemType;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

public class ItemTypeConverter implements CustomConverter
{
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass)
    {
        if (source == null)
            return null;

        if (!(source instanceof ItemType))
            throw new MappingException(ItemTypeConverter.class.getName() + " used incorrectly.  Source object is not type "
                + ItemType.class.getName());

        return source.toString();
    }
}
