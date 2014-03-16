package com.blstream.myhoard.db.dao;

import java.util.List;
import org.hibernate.Session;
import com.blstream.myhoard.db.model.*;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserDAO implements ResourceDAO<UserDS> {

    private SessionFactory sessionFactory;

    @Override
    public List<UserDS> getList() {
        Session session = sessionFactory.getCurrentSession();
        List<UserDS> result = session.createQuery("from UserDS").list();
        return result;
    }

    @Override
    public UserDS get(int id) {
        Session session = sessionFactory.getCurrentSession();
        UserDS result = (UserDS) session.createQuery("from UserDS where id = " + id).uniqueResult();
        return result;
    }
    
    @Override
    public void create(UserDS obj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
    }

    @Override
    public void update(UserDS obj) {
        UserDS object = get(obj.getId());
        object.updateObject(obj);
        Session session = sessionFactory.getCurrentSession();
        session.update(object);
        obj.updateObject(object);
    }

    @Override
    public void remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete UserDS where id = " + id).executeUpdate();
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


}