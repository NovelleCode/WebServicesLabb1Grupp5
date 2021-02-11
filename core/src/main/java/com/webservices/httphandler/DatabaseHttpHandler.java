package com.webservices.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webservices.models.Person;
import com.webservices.models.PersonDAO;
import com.webservices.models.PersonDAOImpl;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class DatabaseHttpHandler implements HttpHandler {

    private static PersonDAO pdao = new PersonDAOImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String getURL = null;
        System.out.println(exchange.getRequestMethod());
        if ("GET".equals(exchange.getRequestMethod())) {
            //getURL = exchange.getRequestURI().toString().substring(1);
            System.out.println(exchange.getRequestURI());
        } else if ("POST".equals(exchange.getRequestMethod())) {
            //  getURL = exchange.getRequestURI().toString().substring(1) + ".html";
            System.out.println(exchange.getRequestURI());

        }
        handleGetResponse(exchange, getURL);
        System.out.println(getURL);
    }

    private static void handleGetResponse(HttpExchange exchange, String getURL) throws IOException {

        BufferedReader httpInput = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "UTF-8"));
        StringBuilder in = new StringBuilder();
        String input;
        while ((input = httpInput.readLine()) != null) {
            in.append(input).append(" ");
        }
        if (exchange.getRequestMethod().equals("POST")) {
            System.out.println(in);
            String body = in.toString();
            String fName = StringUtils.substringBetween(body, "fname=", "&");
            String lname = StringUtils.substringAfter(body, "lname=").trim();

            System.out.println("Firstname:" + fName);
            System.out.println("Lastname:" + lname);

            Person p = new Person(fName, lname);
            pdao.create(p);

        }

        httpInput.close();

        String json = createJsonResponse();
        OutputStream outputStream = exchange.getResponseBody();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, json.length());

        outputStream.write(json.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private static String createJsonResponse() {

        var list = pdao.getAll();

        JsonConverter converter = new JsonConverter();

        var json = converter.convertToJson(list);
        System.out.println(json);
        return json;
    }




}
