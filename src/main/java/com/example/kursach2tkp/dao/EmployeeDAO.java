package com.example.kursach2tkp.dao;

import com.example.kursach2tkp.models.Employee;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Repository
@Transactional
public class EmployeeDAO {


    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    public List<Employee> getAllWorkersList(){
        return  sessionFactory.getCurrentSession().createQuery("from Employee").list();
    }

    public void addNewEmployees(Employee employee){
        sessionFactory.getCurrentSession().persist(employee);
    }

    public void updateEmployees(Employee employee){
        sessionFactory.getCurrentSession().update(employee);
    }

    public void deleteEmployees(Employee employee){
        sessionFactory.getCurrentSession().delete(employee);
    }

    public Employee getEmployeesByID(int id){
        return  sessionFactory.getCurrentSession().get(Employee.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Employee> getWorkersByDisciplineID(int id){
        return sessionFactory.getCurrentSession().createQuery("from Employee where subject_id=" + id).list();
    }

    public void deleteEmployeesById(int id){
        sessionFactory.getCurrentSession().delete(getEmployeesByID(id));
    }
}
