package com.webservices.plugin;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webservices.annotations.Route;

import java.io.IOException;

@Route("/easteregg")
public class PluginHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Plugg");
        switch (exchange.getRequestMethod()) {
            case "HEAD":
                //handleHeaderResponse(exchange);
                break;
            case "GET":
                //handleHeaderResponse(exchange);
                //handleBodyResponse(exchange);
                // Load some funny image or some javascript function
                break;
            default:
                System.out.println("Konstig request");
                System.out.println(exchange.getRequestMethod());
                break;
        }
    }
}
