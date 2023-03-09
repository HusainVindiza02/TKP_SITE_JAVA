package com.example.kursach2tkp.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "positions")
public class Position {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "position")
    private Set<Position> position = new HashSet<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Position> getPost() {
        return position;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(Set<Position> position) {
        this.position = position;
    }
}
