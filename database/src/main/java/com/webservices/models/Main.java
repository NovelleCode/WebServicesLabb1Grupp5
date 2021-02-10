package com.webservices.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        PersonDAO pdao = new PersonDAOImpl();
        Person p = new Person("Mickey", "mouse");
        pdao.create(p);
        System.out.println(pdao.getAll());
    }

}
