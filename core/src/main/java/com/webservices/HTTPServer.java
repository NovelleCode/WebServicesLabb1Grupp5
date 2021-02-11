package com.webservices;

import com.sun.net.httpserver.HttpServer;
import com.webservices.httphandler.MyHttpHandler;
import com.webservices.httphandler.NamesHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
        server.createContext("/index.html", new MyHttpHandler());
        server.createContext("/cat.png", new MyHttpHandler());
        server.createContext("/sheet.pdf", new MyHttpHandler());
        server.createContext("/postinfo.html", new MyHttpHandler());
        server.createContext("/style.css",new MyHttpHandler());
        server.createContext("/func.js",new MyHttpHandler());

        server.createContext("/result", new NamesHttpHandler());
        // replace nameshttphandler with databasehandler

        ExecutorService executorService = Executors.newCachedThreadPool();
        server.setExecutor(executorService);
        server.start();
    }

    /*
    * 1.Hämta data med GET, konverterat till JSON
    * 2.Kunna skicka data via JSON till Databasen
    * 3.Egen Handler för Javascript filer.
    * 4.Ladda in plugins via Serviceloader Dynamiskt.
    * 5.SPI för extenda och använda sig utav Dependency injections
    *
    *
    *
    *
    *
    *
    * */

}
