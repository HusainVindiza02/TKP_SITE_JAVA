package com.example.kursach2tkp.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "disciplines")
public class Disciplines {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplines")
    private Set<Employee> employees;

    public Disciplines(){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Employee> getWorker() {
        return employees;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorker(Set<Employee> employee) {
        this.employees = employee;
    }
}
