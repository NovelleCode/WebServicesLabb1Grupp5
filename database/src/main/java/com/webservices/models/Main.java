package com.webservices.models;

public class Main {
    public static void main(String[] args) {
        PersonDAO pdao = new PersonDAOImpl();

        Person p = new Person("karen", "batjes");

        pdao.create(p);
        System.out.println(pdao.getAll());
    }

}
