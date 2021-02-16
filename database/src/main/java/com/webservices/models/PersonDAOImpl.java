package com.webservices.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class PersonDAOImpl implements PersonDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");

    @Override
    public void createAndAddPerson(String firstName, String lastName) {
        Person p = new Person(firstName, lastName);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    @Override
    public List<Person> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Person> list = em.createQuery("from Person", Person.class).getResultList();
        em.getTransaction().commit();
        return list;
    }
}
