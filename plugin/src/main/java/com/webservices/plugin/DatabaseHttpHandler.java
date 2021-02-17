package com.webservices.plugin;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webservices.models.PersonDAO;
import com.webservices.models.PersonDAOImpl;
import com.webservices.converters.JsonConverter;
import com.webservices.constants.NameConstants;
import com.webservices.annotations.Route;
import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

@Route("/result")

public class DatabaseHttpHandler implements HttpHandler {
    public PersonDAO pdao;

    public DatabaseHttpHandler() {
        this.pdao = new PersonDAOImpl();
    }

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

    private void handlePostRequest (HttpExchange exchange) throws IOException {
        String body = getFirstNameLastNameInput(exchange);
        String fName = getFirstName(body);
        String lName = getLastName(body);
        pdao.createAndAddPerson(fName, lName);
    }

    private void handleHeaderResponse(HttpExchange exchange) throws IOException {
        String json = createJsonResponse();
        exchange.getResponseHeaders().set(NameConstants.CONTENTTYPE, NameConstants.CONTENTTYPEJSON);
        exchange.getResponseHeaders().set(NameConstants.CONTENTLENGTH, String.valueOf(json.length()));
        exchange.sendResponseHeaders(200, json.length());
    }

    private void handleBodyResponse(HttpExchange exchange) throws IOException {
        String json = createJsonResponse();
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(json.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private String getFirstNameLastNameInput(HttpExchange exchange) throws IOException {
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

    private String getLastName(String body) {
        return StringUtils.substringAfter(body, "lname=").trim();
    }

    private String getFirstName(String body) {
        return StringUtils.substringBetween(body, "fname=", "&");
    }

    private String createJsonResponse() {
        var list = pdao.getAll();
        JsonConverter converter = new JsonConverter();
        var json = converter.convertToJson(list);
        return json;
    }
}
