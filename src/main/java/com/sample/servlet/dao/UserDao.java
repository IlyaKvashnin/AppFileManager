package com.sample.servlet.dao;

import com.sample.servlet.infrastructure.models.User;
import com.sample.servlet.infrastructure.services.DbService;
import jakarta.persistence.PersistenceException;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User> {

    @Override
    public Optional<User> get(String login) {
        Session currentSession = DbService.getSessionFactory().getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        User user = currentSession.byNaturalId(User.class).using("login", login).load();
        transaction.commit();
        currentSession.close();

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        Session currentSession = DbService.getSessionFactory().getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();

        HibernateCriteriaBuilder cb = currentSession.getCriteriaBuilder();
        JpaCriteriaQuery<User> cq = cb.createQuery(User.class);
        JpaRoot<User> rootEntry = cq.from(User.class);
        JpaCriteriaQuery<User> all = cq.select(rootEntry);
        Query<User> allQuery = currentSession.createQuery(all);

        transaction.commit();
        currentSession.close();

        return allQuery.getResultList();
    }

    @Override
    public boolean save(User user) {
        boolean isSave = true;
        Session currentSession = DbService.getSessionFactory().getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();

        try {
            currentSession.persist(user);
        } catch (PersistentObjectException e) {
            isSave = false;
        }

        transaction.commit();
        currentSession.close();

        return isSave;
    }

    @Override
    public boolean update(User user) {
        boolean isSave = true;
        Session currentSession = DbService.getSessionFactory().getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();

        try {
            currentSession.merge(user);
        } catch (PersistenceException e) {
            isSave = false;
        }

        transaction.commit();
        currentSession.close();

        return isSave;
    }

    @Override
    public boolean delete(User user) {
        boolean isSave = true;
        Session currentSession = DbService.getSessionFactory().getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();

        try {
            currentSession.remove(user);
        } catch (PersistentObjectException e) {
            isSave = false;
        }

        transaction.commit();
        currentSession.close();

        return isSave;
    }
}