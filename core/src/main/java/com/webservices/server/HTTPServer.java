package com.webservices.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.webservices.plugin.Route;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8002), 0);

        ServiceLoader<HttpHandler> loader = ServiceLoader.load(HttpHandler.class);
        for (HttpHandler httpHandler : loader) {
            System.out.println(httpHandler.getClass().getAnnotation(Route.class).value());
            System.out.println(httpHandler);

            server.createContext(httpHandler.getClass().getAnnotation(Route.class).value(), httpHandler);
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        server.setExecutor(executorService);
        server.start();
    }
}


