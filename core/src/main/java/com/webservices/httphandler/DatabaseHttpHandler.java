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
        String requestMethod = exchange.getRequestMethod();
        switch(requestMethod) {
            case "HEAD":
                handleHeadResponse(exchange);
                // M1 : Only header information as a response
                break;
            case "GET":
                handleGetResponse(exchange);
                // M1 :Header information as a response ++++
                // M2 :Response body
                break;
            case "POST":
                // M1 :Only header information as a response ++++
                // M2 :Response body
                // M3. Prepare post request information cq: take in firstname, lastname, create person, add person to table
                handlePostResponse(exchange);
                break;
            default:
                // something here too?
                break;
        }
    }

    private void handleHeadResponse(HttpExchange exchange) {
    }


    private static void handlePostResponse(HttpExchange exchange) throws IOException {
        String body = getFirstNameLastNameInput(exchange);
        String fName = getFirstName(body);
        String lName = getLastName(body);
        createAndAddPerson(fName, lName);

        String json = createJsonResponse();
        OutputStream outputStream = exchange.getResponseBody();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("Content-Length", String.valueOf(json.length()));
        exchange.sendResponseHeaders(200, json.length());

        outputStream.write(json.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private static void handleGetResponse(HttpExchange exchange) throws IOException {

        String json = createJsonResponse();
        OutputStream outputStream = exchange.getResponseBody();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("Content-Length", String.valueOf(json.length()));
        exchange.sendResponseHeaders(200, json.length());

        outputStream.write(json.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private static void createAndAddPerson(String fName, String lName) {
        Person p = new Person(fName, lName);
        pdao.create(p);
    }

    private static String getFirstNameLastNameInput(HttpExchange exchange) throws IOException {
        BufferedReader httpInput = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "UTF-8"));
        StringBuilder in = new StringBuilder();
        String input;
        while ((input = httpInput.readLine()) != null) {
            in.append(input).append(" ");
        }
        httpInput.close();
        return in.toString();
    }

    private static String getLastName(String body) {
        String lname = StringUtils.substringAfter(body, "lname=").trim();
        return lname;
    }

    private static String getFirstName(String body) {
        String fName = StringUtils.substringBetween(body, "fname=", "&");
        return fName;
    }

    private static String createJsonResponse() {
        var list = pdao.getAll();
        JsonConverter converter = new JsonConverter();
        var json = converter.convertToJson(list);
        System.out.println(json);
        return json;
    }


}
