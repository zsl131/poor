package com.zslin.basic.repository;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2017/1/6 14:48.
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final EntityManager em;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
    }

    @Override
    public List<T> listBySql(String sql, Object... args) {
        Query query = em.createNativeQuery(sql);
        int i=1;
        for(Object arg : args) {
            query.setParameter(i++, arg);
        }
        return query.getResultList();
    }

    @Override
    public List<T> listByHql(String hql, Object... args) {
        Query query = em.createQuery(hql);
        int i=1;
        for(Object arg : args) {
            query.setParameter(i++, arg);
        }
        return query.getResultList();
    }

    @Override
    public void updateBySql(String sql, Object... args) {
        Query query = em.createNativeQuery(sql);
        int i=1;
        for(Object arg : args) {
            query.setParameter(i++, arg);
        }
        query.executeUpdate();
    }

    @Override
    public void updateByHql(String hql, Object... args) {
        Query query = em.createQuery(hql);
        int i=1;
        for(Object arg : args) {
            query.setParameter(i++, arg);
        }
        query.executeUpdate();
    }

    @Override
    public Object queryByHql(String hql, Object... args) {
        List list = listByHql(hql, args);
        return (list==null||list.size()<=0)?null:list.get(0);
    }
}
