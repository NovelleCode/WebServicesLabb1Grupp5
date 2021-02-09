package com.webservices.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webservices.fileutils.FileReader;

import java.io.*;
import java.nio.file.Files;

public class MyHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String requestParamValue = null;
        System.out.println(exchange.getRequestMethod());
        if ("GET".equals(exchange.getRequestMethod()) || "HEAD".equals(exchange.getRequestMethod())) {

            requestParamValue = formatRequestUri(exchange);
            System.out.println(requestParamValue);
        }

        handleResponse(exchange, requestParamValue);

    }

    private String formatRequestUri(HttpExchange exchange) {
        return exchange.getRequestURI().toString().substring(1);
    }

    private void handleResponse(HttpExchange exchange, String requestParamValue)  throws  IOException {
        OutputStream outputStream = exchange.getResponseBody();

        File file = new File("files/" + requestParamValue);
        byte[] page = FileReader.readFromFile(file);

        String content = Files.probeContentType(file.toPath());
        System.out.println(content);

        exchange.getResponseHeaders().set("Content-Type", content);
        exchange.getResponseHeaders().set("Content-Length", String.valueOf(page.length));
        exchange.sendResponseHeaders(200, page.length);

        if("GET".equals(exchange.getRequestMethod())){
            outputStream.write(page);
        }

        outputStream.flush();
        outputStream.close();
    }
}

