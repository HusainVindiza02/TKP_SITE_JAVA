package com.example.kursach2tkp.dao;

import com.example.kursach2tkp.models.Position;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Component
@Repository
@Transactional
public class PositionDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Position getPositionById(int id){
        return sessionFactory.getCurrentSession().get(Position.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Position> getPositionList(){
        return sessionFactory.getCurrentSession().createQuery("from Position").list();
    }

    public Position getPositionByName(String name){
        return (Position) sessionFactory.getCurrentSession().createQuery("from Position where name='" + (String) name.toLowerCase(Locale.ROOT) + "'").uniqueResult();
    }

    public void addPost(Position position){
        sessionFactory.getCurrentSession().persist(position);
    }
}
