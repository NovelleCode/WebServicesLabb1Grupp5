package com.webservices;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsExchange;
import com.webservices.spi.Page;

import java.util.ServiceLoader;

public class PluginLoader {
    public static void main(String[] args) {
      /*ServiceLoader<TestGreeting> loader = ServiceLoader.load(TestGreeting.class);
        Set<TestGreeting> pages = loader.stream().filter(p->p.type().isAnnotationPresent(Adress.class)&&
                p.type().getAnnotation(Adress.class).value().equals("/Greeting")).map(ServiceLoader.Provider::get).collect(Collectors.toSet());

        System.out.println("Test");


        for (TestGreeting greeting:loader){
            if(greeting.getClass().getAnnotation(Adress.class).value().equals("/Greeting")){
                System.out.println("HALLLÅ");
                greeting.printGreeting();
            }
        }*/

//        TestGreeting greeting = new TestGreeting();
//        System.out.println(greeting.getClass().getAnnotation(Adress.class).value());

        ServiceLoader<HttpHandler> loader = ServiceLoader.load(HttpHandler.class);
        System.out.println("Test");

        for (HttpHandler httpHandler : loader){
//            httpHandler.getClass().getAnnotation(Adress.class).value();
            System.out.println(httpHandler);
        }

    }
}
