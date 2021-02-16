package com.webservices.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webservices.models.HandlePerson;
import com.webservices.plugin.Route;
import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

@Route("/result")

public class DatabaseHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getRequestMethod());
        System.out.println(exchange.getRequestURI());
        switch(exchange.getRequestMethod()) {
            case "HEAD":
                handleHeaderResponse(exchange);
                break;
            case "GET":
                handleHeaderResponse(exchange);
                handleBodyResponse(exchange);
                break;
            case "POST":
                handlePostRequest(exchange);
                handleHeaderResponse(exchange);
                handleBodyResponse(exchange);
                break;
            default:
                System.out.println("Konstig request");
                System.out.println(exchange.getRequestMethod());
                break;
        }
    }

    private static void handlePostRequest (HttpExchange exchange) throws IOException {
        String body = getFirstNameLastNameInput(exchange);
        String fName = getFirstName(body);
        String lName = getLastName(body);
        HandlePerson.createAndAddPerson(fName, lName);
    }

    private static void handleHeaderResponse(HttpExchange exchange) throws IOException {
        String json = createJsonResponse();
        exchange.getResponseHeaders().set(NameConstants.CONTENTTYPE, NameConstants.CONTENTTYPEJSON);
        exchange.getResponseHeaders().set(NameConstants.CONTENTLENGTH, String.valueOf(json.length()));
        exchange.sendResponseHeaders(200, json.length());
    }

    private static void handleBodyResponse(HttpExchange exchange) throws IOException {
        String json = createJsonResponse();
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(json.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private static String getFirstNameLastNameInput(HttpExchange exchange) throws IOException {
        BufferedReader httpInput = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "UTF-8"));
        StringBuilder in = new StringBuilder();
        String input;
        while ((input = httpInput.readLine()) != null) {
            in.append(input).append(" ");
        }
        httpInput.close();
        System.out.println(in.toString());
        return in.toString();
    }

    private static String getLastName(String body) {
        return StringUtils.substringAfter(body, "lname=").trim();
    }

    private static String getFirstName(String body) {
        return StringUtils.substringBetween(body, "fname=", "&");
    }

    private static String createJsonResponse() {
        var list = HandlePerson.getAllPersons();
        JsonConverter converter = new JsonConverter();
        var json = converter.convertToJson(list);
        return json;
    }
}
