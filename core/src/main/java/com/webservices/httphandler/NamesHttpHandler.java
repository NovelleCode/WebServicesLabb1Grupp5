package com.webservices.httphandler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webservices.fileutils.FileReader;
import com.webservices.json.Name;
import com.webservices.json.Names;
import org.apache.commons.lang3.StringUtils;


import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;



public class NamesHttpHandler implements HttpHandler {

    private static ArrayList<Name> names = new ArrayList<>();

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

        BufferedReader httpInput = new BufferedReader(new InputStreamReader(
                exchange.getRequestBody(), "UTF-8"));
        StringBuilder in = new StringBuilder();
        String input;
        while ((input = httpInput.readLine()) != null) {
            in.append(input).append(" ");
        }
        if(exchange.getRequestMethod().equals("POST")) {
            System.out.println(in);
            String body = in.toString();
            String fName = StringUtils.substringBetween(body, "fname=","&");
            String lname = StringUtils.substringAfter(body, "lname=").trim();

            System.out.println("Firstname:" + fName);
            System.out.println("Lastname:" + lname);

            Name n = new Name(fName, lname);
            names.add(n);
            for (Name name: names) {
                System.out.println(name.toString());
            }
        }


        httpInput.close();

        OutputStream outputStream = exchange.getResponseBody();
        File file = new File("files/" + requestParamValue);
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
