/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dto.client;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientFetchDTO<T> extends ClientDTO<T>
{
    protected int startRow;

    protected int endRow;

    protected Map<String, Object> data;

    protected Map<String, Boolean> orderBy;

    public void setStartRow(int startRow)
    {
        this.startRow = startRow;
    }

    public void setEndRow(int endRow)
    {
        this.endRow = endRow;
    }

    public void setData(Map<String, Object> data)
    {
        this.data = data;
    }

    public void setSortBy(String[] fields)
    {
        orderBy = new LinkedHashMap<String, Boolean>();

        for (String field : fields)
        {
            // Field names starting with a hyphen indicate descending sort order
            if (field.startsWith("-"))
                orderBy.put(field.substring(1), false);
            else
                orderBy.put(field, true);
        }
    }

    public int getStartRow()
    {
        return startRow;
    }

    public int getEndRow()
    {
        return endRow;
    }

    public int getTotalRows()
    {
        return endRow - startRow;
    }

    public Map<String, Boolean> getOrderBy()
    {
        return orderBy;
    }

    public Map<String, Object> getData()
    {
        return data;
    }
}
