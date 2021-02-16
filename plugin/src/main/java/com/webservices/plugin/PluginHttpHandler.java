package com.webservices.plugin;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

@Adress("/hej")
public class PluginHttpHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Hej från plugon");
    }
}
