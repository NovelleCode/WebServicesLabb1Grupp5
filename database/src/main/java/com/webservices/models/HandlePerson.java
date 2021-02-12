package com.webservices.models;

import java.util.List;

public class HandlePerson {

    public static void createAndAddPerson(String fName, String lName) {
        PersonDAO pdao = new PersonDAOImpl();
        Person p = new Person(fName, lName);
        pdao.create(p);
    }

    public static List<Person> getAllPersons(){
        PersonDAO pdao = new PersonDAOImpl();
        return pdao.getAll();
    }
}
