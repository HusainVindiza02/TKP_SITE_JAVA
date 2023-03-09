package com.example.kursach2tkp.dao;


import com.example.kursach2tkp.models.Disciplines;
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
public class DisciplineDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createDisciplines(Disciplines disciplines){
        sessionFactory.getCurrentSession().persist(disciplines);
    }

    public Disciplines getDisciplinesById(int id){
        return sessionFactory.getCurrentSession().get(Disciplines.class, id);
    }

    public Disciplines getDisciplinesByName(String name){
        return (Disciplines) sessionFactory.getCurrentSession().createQuery("from Disciplines where name='" + (String) name.toLowerCase(Locale.ROOT) + "'").uniqueResult();
    }

    public void deleteDisciplinesById(int id){
        sessionFactory.getCurrentSession().delete(getDisciplinesById(id));
    }

    @SuppressWarnings("unchecked")
    public List<Disciplines> getAllDisciplesList(){
        return sessionFactory.getCurrentSession().createQuery("from Disciplines").list();
    }
}
