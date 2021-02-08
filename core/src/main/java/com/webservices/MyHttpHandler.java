package com.webservices;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MyHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String requestParamValue = null;
        System.out.println(exchange.getRequestMethod());
        if("GET".equals(exchange.getRequestMethod())) {

            requestParamValue = exchange.getRequestURI().toString().substring(1);
            System.out.println(requestParamValue);
        }
//           handleGetRequest(exchange);
//        } else if("POST".equals(exchange)) {
//            requestParamValue = handlePostRequest(exchange);
//        }

        handleResponse(exchange,requestParamValue);
    }
//    private String handleGetRequest(HttpExchange httpExchange) {
//        return httpExchange.
//        getRequestURI().toString().split("\\?")[1].split("=")[1];
//    }
    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
        OutputStream outputStream = httpExchange.getResponseBody();

        File file = new File("Files/" + requestParamValue);
        byte[] page = new MyHttpHandler().readFromFile(file);

        String content = Files.probeContentType(file.toPath());
        System.out.println(content);

        httpExchange.getResponseHeaders().set("Content-Type", content);
        httpExchange.sendResponseHeaders(200, page.length);

        outputStream.write(page);

        outputStream.flush();
        outputStream.close();
    }

    private byte[] readFromFile(File file) {
        byte[] content = new byte[0];
        System.out.println("Does file exists: " + file.exists());
        if (file.exists() && file.canRead()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                content = new byte[(int)file.length()];
                int count = fileInputStream.read(content);
                System.out.println(count);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }
}

