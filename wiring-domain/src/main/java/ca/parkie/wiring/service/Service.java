/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service;

import java.util.List;
import java.util.Map;

public interface Service<T>
{
    T saveEntity(T entity);
    T retrieveEntity(int id);
    T retrieveEntity(Map<String, Object> params);
    List<T> getEntityList(Map<String, Object> params, Map<String, Boolean> orderBy, int start, int count);
    void removeEntity(T entity);
}
