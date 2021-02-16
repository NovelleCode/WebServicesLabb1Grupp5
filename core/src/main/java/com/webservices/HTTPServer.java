package com.webservices;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.webservices.httphandler.DatabaseHttpHandler;
import com.webservices.httphandler.FilesHttpHandler;
import com.webservices.plugin.Route;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8002), 0);
        server.createContext("/", new FilesHttpHandler());

        server.createContext("/result", new DatabaseHttpHandler());

        ServiceLoader<HttpHandler> loader = ServiceLoader.load(HttpHandler.class);
        for (HttpHandler httpHandler : loader){
            System.out.println(httpHandler.getClass().getAnnotation(Route.class).value());
            System.out.println(httpHandler);
        }


        ExecutorService executorService = Executors.newCachedThreadPool();
        server.setExecutor(executorService);
        server.start();
    }

    /*
    Att göra:

    Frågor till Martin
    - Hur gör vi med databas vid inlämning? Behöver du den? I molnet?
      Kolla på codefirst istället, så att tabellen skapas automatiskt.
      Hans kan skapa en databas på server som alla kan nå

    - clientmodulen bara innehåller test.http
      Om vi har lust och tid kan vi lägga till en Clientsocket klass


    - VG KRAV - PLUGIN
      Vad behövs för VG, kan du förklara?
    - Tips på plugins?
      Databashandler som plugin. Nu är den statiskt
      Ladda in den dynamiskt genom ServiceLoader

    - SPI för extenda och använda sig utav Dependency injections

      Branchförslag 1: Göra om befintlig databashandler så att den laddas in dynamiskt
      Branchförslag 2: Skapa en ny databashandler som laddas in dynamiskt.


    "Pluginklasser ska ha ett definierat interface att extenda och använda sig av dependency injection för
    att få objekt att hämta inkommande information via och skicka utgående information till.
    Webb servern behöver alltså tillhandahålla någon form av (http)Request och (http)Response objekt"

    Detta står en bit upp i labbbeskrivningen.
    Webb servern kunna hantera pluginklasser skrivna i Java som kan laddas in dynamiskt.
    Konfiguration av routing m.m. som behövs för pluginen ska göras med runtime annotations.



    * */

}
