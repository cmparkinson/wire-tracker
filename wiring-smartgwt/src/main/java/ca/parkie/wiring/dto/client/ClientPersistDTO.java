/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dto.client;

public class ClientPersistDTO<T> extends ClientDTO<T>
{
    protected T data;
    protected T oldValues;

    public T getNewValues()
    {
        return this.data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public T getOldValues()
    {
        return oldValues;
    }

    public void setOldValues(T oldValues)
    {
        this.oldValues = oldValues;
    }
}
