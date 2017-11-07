package com.internshaala.internshalaworkshops;

/**
 * Created by vinee_000 on 07-11-2017.
 */

public class Person {
    String personId,name,email,password,workshops="";

    public Person() {
    }

    public Person(String personId, String name, String email, String password, String workshops) {
        this.personId = personId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.workshops = workshops;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWorkshops() {
        return workshops;
    }

    public void setWorkshops(String workshops) {
        this.workshops = workshops;
    }

}
