package com.webservices.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class PersonDAOImpl implements PersonDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");

    @Override
    public void create(Person p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    @Override
    public List<Person> getAll() {
        List<Person> list;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        list = em.createQuery("from Person", Person.class).getResultList();
        em.getTransaction().commit();
        return list;
    }
}
