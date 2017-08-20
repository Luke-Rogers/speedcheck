package com.speedcheck.repository;

import com.speedcheck.model.Result;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public abstract class BaseRepository<T> {

    @Autowired
    private SessionFactory sessionFactory;

    protected abstract Class<T> getType();

    protected Criteria getCriteria() {
        return getSession().createCriteria(getType());
    }

    public Collection<Result> findAll() {
        return getCriteria().list();
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(T save) {
        getSession().save(save);
    }

}
