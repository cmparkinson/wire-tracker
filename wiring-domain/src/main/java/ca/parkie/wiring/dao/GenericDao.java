/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dao;

import java.util.List;
import java.util.Map;

public interface GenericDao<T>
{
    T findById(int id);
    T findByMap(Map<String, Object> map);
    T persistOrMerge(T t);
    void remove(T t);
    List<T> list(Map<String, Object> params, Map<String, Boolean> orderBy, int start, int count);
}
