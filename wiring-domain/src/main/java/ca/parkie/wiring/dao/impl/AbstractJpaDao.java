/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.dao.impl;

import ca.parkie.wiring.dao.GenericDao;
import ca.parkie.wiring.entity.PersistentEntity;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractJpaDao<T extends PersistentEntity> implements GenericDao<T>
{
    private Class<T> clazz;

    @PersistenceContext
    private EntityManager em;

    public AbstractJpaDao(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    public EntityManager getEntityManager()
    {
        return em;
    }

    public T findByMap(Map<String, Object> map)
    {
        CriteriaQuery<T> query = buildCriteriaQuery(map);
        return em.createQuery(query).getSingleResult();
    }

    public T findById(int id)
    {
        return em.find(clazz, id);
    }

    public T persistOrMerge(T t)
    {
        return em.merge(t);
    }

    public void remove(T t)
    {
        // Make sure the object is attached to this persistence context
        if (!em.contains(t))
            t = em.merge(t);

        em.remove(t);
    }

    public List<T> list(Map<String, Object> params, Map<String, Boolean> orderBy, int start, int count)
    {
        CriteriaQuery<T> criteriaQuery = buildCriteriaQuery(params, orderBy);
        TypedQuery<T> query = em.createQuery(criteriaQuery);
        query.setFirstResult(start);
        query.setMaxResults(count);
        return query.getResultList();
    }

    protected CriteriaQuery<T> buildCriteriaQuery(Map<String, Object> map)
    {
        return buildCriteriaQuery(map, null);
    }

    protected CriteriaQuery<T> buildCriteriaQuery(Map<String, Object> map, Map<String, Boolean> orderBy)
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        Root<T> root = query.from(clazz);

        List<Order> orderList = new ArrayList<Order>();
        if (orderBy != null)
        {
            for (Map.Entry<String, Boolean> entry : orderBy.entrySet())
            {
                Order order;
                String key = entry.getKey();

                // ASC == true
                if (entry.getValue())
                    order = cb.asc(root.<Object>get(key));
                else
                    order = cb.desc(root.<Object>get(key));

                orderList.add(order);
            }
        }

        Predicate predicate = cb.conjunction();
        for (Map.Entry<String, Object> p : map.entrySet())
            predicate = cb.and(predicate, cb.equal(root.<Object>get(p.getKey()), p.getValue()));

        if (orderList.size() > 0)
            return query.where(predicate).orderBy(orderList);

        return query.where(predicate);
    }
}