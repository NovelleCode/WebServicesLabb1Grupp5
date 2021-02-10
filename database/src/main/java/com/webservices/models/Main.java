package com.webservices.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Person o = em.find(Person.class, 1);
        System.out.println(o);
        em.getTransaction().commit();


        }
    @Override
    public List<Person> getAll() {
        List<Person> list;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        list = em.createQuery("select u from User u", Person.class).getResultList();
        em.getTransaction().commit();
        return list;
    }
}
