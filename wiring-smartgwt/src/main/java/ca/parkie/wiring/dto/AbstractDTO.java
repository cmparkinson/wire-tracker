/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dto;

import org.codehaus.jackson.annotate.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractDTO implements DataTransferObject
{
    protected final Map<String, Object> unknownPropertyMap = new HashMap<String, Object>();

    @JsonAnySetter
    public void handleUnknownProperty(String key, Object value)
    {
        unknownPropertyMap.put(key, value);
    }
}
