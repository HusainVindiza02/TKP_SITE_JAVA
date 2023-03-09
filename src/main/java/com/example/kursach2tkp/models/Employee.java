package com.example.kursach2tkp.models;


import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "patronym")
    private String patronym;

    @Column(name = "birthday", nullable = false)
    private String birthday;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Disciplines disciplines;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    public Employee(){

    }

    public void setDiscipline(Disciplines disciplines) {
        this.disciplines = disciplines;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPatronym(String patronym) {
        this.patronym = patronym;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    /*
    public void setPost(String position) {
        this.position = position;
    }

     */

    /*
    Getters
     */

    public Disciplines getDiscipline() {
        return disciplines;
    }

    public int getId(){
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPatronym() {
        return patronym;
    }

    public String getBirthday() {
        return birthday;
    }

    public Position getPosition() {
        return position;
    }
}
