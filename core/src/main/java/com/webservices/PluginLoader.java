package com.webservices;

import com.sun.net.httpserver.HttpHandler;
import com.webservices.plugin.Route;

import java.util.ServiceLoader;

public class PluginLoader {
    public static void main(String[] args) {


        ServiceLoader<HttpHandler> loader = ServiceLoader.load(HttpHandler.class);
        for (HttpHandler httpHandler : loader){
            System.out.println(httpHandler.getClass().getAnnotation(Route.class).value());
            System.out.println(httpHandler);
        }

    }
}
