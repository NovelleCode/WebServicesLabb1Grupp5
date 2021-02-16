module core {
//    uses com.webservices.plugin.Adress;
    uses com.webservices.TestGreeting;
    uses com.webservices.spi.Page;
    uses com.sun.net.httpserver.HttpHandler;
    requires com.webservices.fileutils;
    requires jdk.httpserver;
    requires com.google.gson;
    requires org.apache.commons.lang3;
    requires database;
    requires spi;

}