/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSOHelper;

import java.util.Map;

public class ServerResponse
{
    protected JavaScriptObject raw;
    protected JavaScriptObject custom;
    protected JavaScriptObject response;

    public ServerResponse(JavaScriptObject raw)
    {
        this.raw = raw;
        this.response = JSOHelper.getAttributeAsJavaScriptObject(raw, JSOConstants.RESPONSE);
        this.custom = JSOHelper.getAttributeAsJavaScriptObject(raw, JSOConstants.EXTENSIONS);
    }

    public Record getData()
    {
        return getRecordFromJSOAttribute(response, JSOConstants.DATA);
    }

    public Record getRefreshedRecord()
    {
        return getRecordFromJSOAttribute(custom, JSOConstants.REFRESH);
    }

    public boolean recordNotFound()
    {
        return JSOHelper.getAttributeAsBoolean(custom, JSOConstants.DELETED);
    }

    protected Record getRecordFromJSOAttribute(JavaScriptObject jso, String attr)
    {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = JSOHelper.getAttributeAsMap(jso, attr);

        if (map == null)
            return null;

        Record record = new Record();
        for (String key : map.keySet())
            record.setAttribute(key, map.get(key));

        return record;
    }
}
