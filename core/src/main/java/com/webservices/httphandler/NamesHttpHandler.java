package com.webservices.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webservices.fileutils.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class NamesHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestParamValue = null;
        System.out.println(exchange.getRequestMethod());
        if ("GET".equals(exchange.getRequestMethod())) {
            requestParamValue = exchange.getRequestURI().toString().substring(1);
            System.out.println(exchange.getRequestURI());
        } else if ("POST".equals(exchange.getRequestMethod())) {
            requestParamValue = exchange.getRequestURI().toString().substring(1);
            System.out.println(exchange.getRequestURI());
        }
        handleGetResponse(exchange, requestParamValue);
        System.out.println(requestParamValue);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().toString().split("\\?")[1].split("=")[1];
    }

    private static void handleGetResponse(HttpExchange exchange, String requestParamValue) throws IOException {

        OutputStream outputStream = exchange.getResponseBody();
        File file = new File("Files/" + requestParamValue);
        byte[] page = FileReader.readFromFile(file);

        String content = Files.probeContentType(file.toPath());
        System.out.println(content);

        exchange.getResponseHeaders().set("Content-Type", content);
        exchange.sendResponseHeaders(200, page.length);

        outputStream.write(page);
        outputStream.flush();
        outputStream.close();
    }
}
