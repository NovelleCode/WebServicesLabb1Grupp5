package com.webservices;

import com.sun.net.httpserver.HttpServer;
import com.webservices.httphandler.FilesHttpHandler;
import com.webservices.httphandler.DatabaseHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8002), 0);
        server.createContext("/index.html", new FilesHttpHandler());
        server.createContext("/cat.png", new FilesHttpHandler());
        server.createContext("/sheet.pdf", new FilesHttpHandler());
        server.createContext("/postinfo.html", new FilesHttpHandler());
        server.createContext("/style.css",new FilesHttpHandler());
        server.createContext("/func.js",new FilesHttpHandler());

        server.createContext("/result", new DatabaseHttpHandler());
        // replace nameshttphandler with databasehandler

        ExecutorService executorService = Executors.newCachedThreadPool();
        server.setExecutor(executorService);
        server.start();
    }

    /*
    Att göra:

    * - Ladda in plugins via Serviceloader Dynamiskt.
    * - SPI för extenda och använda sig utav Dependency injections


    Frågor till Martin
    - Hur gör vi med databas vid inlämning? Behöver du den? I molnet?
    - Behöver vi junit-tester?
    - Tips på strukturering ex databasHttpHandler
    - Ok att ha flera URL till samma handler?
    - clientmodulen med test.http, vara kvar?

     Vad behövs för VG, kan du förklara?
    - Tips på plugins?
    - Vad betyder:
    "Pluginklasser ska ha ett definierat interface att extenda och använda sig av dependency injection för
    att få objekt att hämta inkommande information via och skicka utgående information till.
    Webb servern behöver alltså tillhandahålla någon form av Request och Response objekt"

    Detta står en bit upp i labbbeskrivningen.
    Webb servern kunna hantera pluginklasser skrivna i Java som kan laddas in dynamiskt.
    Konfiguration av routing m.m. som behövs för pluginen ska göras med runtime annotations.




    * */

}
