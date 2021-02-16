package com.webservices.models;

import java.util.List;

public interface PersonDAO {
    void createAndAddPerson (String firstName, String lastName);
    // Inserts firstName & lastName in new person and add it to the database

    List<Person> getAll();
    // Returns a list of all Persons in database

}
