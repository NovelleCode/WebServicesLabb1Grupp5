package com.webservices;

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

        TestGreeting greeting = new TestGreeting();
        System.out.println(greeting.getClass().getAnnotation(Adress.class).value());

        /*ServiceLoader<Page> loader = ServiceLoader.load(Page.class);
        System.out.println("Test");

        for (Page page : loader){
            page.execute();
        }*/

    }
}
