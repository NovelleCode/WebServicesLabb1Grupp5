package com.webservices.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webservices.fileutils.FileReader;
import com.webservices.models.Person;
import com.webservices.models.PersonDAO;
import com.webservices.models.PersonDAOImpl;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class NamesHttpHandler implements HttpHandler {

    private static ArrayList<Person> names = new ArrayList<>();
    private static PersonDAO pdao = new PersonDAOImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestParamValue = null;
        System.out.println(exchange.getRequestMethod());
        if ("GET".equals(exchange.getRequestMethod())) {
            requestParamValue = exchange.getRequestURI().toString().substring(1);
            System.out.println(exchange.getRequestURI());
        } else if ("POST".equals(exchange.getRequestMethod())) {
            requestParamValue = exchange.getRequestURI().toString().substring(1) + ".html";
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
        if (exchange.getRequestMethod().equals("POST")) {
            System.out.println(in);
            String body = in.toString();
            String fName = StringUtils.substringBetween(body, "fname=", "&");
            String lname = StringUtils.substringAfter(body, "lname=").trim();

            System.out.println("Firstname:" + fName);
            System.out.println("Lastname:" + lname);


            Person p = new Person(fName, lname);
            pdao.create(p);
            //System.out.println(pdao.getAll());



        }

        httpInput.close();

        String json = createJsonResponse();

        OutputStream outputStream = exchange.getResponseBody();
        File file = new File("files/" + requestParamValue);
        byte[] page = FileReader.readFromFile(file);


        String content = Files.probeContentType(file.toPath());
        System.out.println(content);

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
