package com.webservices.models;

import java.util.List;

public interface PersonDAO {
    void create (Person p);
    // Inserts person P to the Person database

    List<Person> getAll();
    // Returns a list of all Persons in database

}
