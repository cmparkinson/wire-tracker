/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.service.impl;

import ca.parkie.wiring.dao.GenericDao;
import ca.parkie.wiring.entity.PersistentEntity;
import ca.parkie.wiring.service.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


abstract public class AbstractService<T extends PersistentEntity> implements Service<T>
{
    @Transactional
    public T saveEntity(T entity)
    {
        T attached = getDao().findById(entity.getId());

        if (attached != null)
            copyEntity(entity, attached);

        return getDao().persistOrMerge(entity);
    }

    @Transactional(readOnly = true)
    public T retrieveEntity(int id)
    {
        return getDao().findById(id);
    }

    @Transactional(readOnly = true)
    public T retrieveEntity(Map<String, Object> params)
    {
        return getDao().findByMap(params);
    }
    @Transactional(readOnly = true)
    public List<T> getEntityList(Map<String, Object> params, Map<String, Boolean> orderBy, int start, int count)
    {
        return getDao().list(params, orderBy, start, count);
    }

    @Transactional
    public void removeEntity(T entity)
    {
        T attached = getDao().findById(entity.getId());
        if (attached == null)
        {
            // TODO Log this
            return;
        }
        copyEntity(entity, attached);
        getDao().remove(attached);
    }

    abstract protected void copyEntity(T source, T destination);

    abstract protected GenericDao<T> getDao();
}
