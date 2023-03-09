package com.example.kursach2tkp.dao;

import com.example.kursach2tkp.models.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Component
@Repository
@Transactional
public class UserDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User getUserByLogin(String login){
        return (User) sessionFactory.getCurrentSession().createQuery("from User where login='" + (String) login.toLowerCase(Locale.ROOT) + "'").uniqueResult();
    }

    public User getUserById(int id){
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    public void createUser(User user){
        sessionFactory.getCurrentSession().persist(user);
    }
}
