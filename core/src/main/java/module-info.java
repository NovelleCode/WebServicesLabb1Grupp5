module core {
    uses com.webservices.Adress;
    uses com.webservices.TestGreeting;
    uses com.webservices.spi.Page;
    requires com.webservices.fileutils;
    requires jdk.httpserver;
    requires com.google.gson;
    requires org.apache.commons.lang3;
    requires database;
    requires spi;

}