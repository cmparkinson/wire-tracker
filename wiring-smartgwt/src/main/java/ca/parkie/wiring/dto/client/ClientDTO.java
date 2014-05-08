/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dto.client;

import org.codehaus.jackson.annotate.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class ClientDTO<T>
{
    // Only useful for debugging purposes
    private Map<String, Object> unknownProperties = new HashMap<String, Object>();

    @JsonAnySetter
    public void handleUnknownProperty(String key, Object value)
    {
        // TODO Remove for production
        unknownProperties.put(key, value);
    }
}
