package com.webservices.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webservices.fileutils.FileReader;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;

import com.webservices.NameConstants;

public class FilesHttpHandler implements HttpHandler {

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
            default:
                System.out.println("Konstig request");
                System.out.println(exchange.getRequestMethod());
                break;
        }
    }

    private String formatRequestUri(HttpExchange exchange) {
        return exchange.getRequestURI().toString().substring(1);
    }

    private void handleHeaderResponse(HttpExchange exchange) throws IOException {
        File file = new File(NameConstants.FILES + File.separator + formatRequestUri(exchange));
        String contentType = Files.probeContentType(file.toPath());

        if (contentType == null || contentType.isEmpty()){
            contentType = getContentTypeForNotDetected(formatRequestUri(exchange));
        }

        exchange.getResponseHeaders().set(NameConstants.CONTENTTYPE, contentType);
        exchange.getResponseHeaders().set(NameConstants.CONTENTLENGTH, String.valueOf(file.length()));
        exchange.sendResponseHeaders(200, file.length());
    }

    private void handleBodyResponse(HttpExchange exchange) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();

        File file = new File(NameConstants.FILES + File.separator + formatRequestUri(exchange));
        byte[] page = FileReader.readFromFile(file);

        outputStream.write(page);
        outputStream.flush();
        outputStream.close();
    }

    private String getContentTypeForNotDetected(String getURL) {
        String content = "";
        String fileExtension = StringUtils.substringAfter(getURL, ".").trim();
        if (fileExtension.equals("js")) {
            content = NameConstants.CONTENTTYPEJS;
        }
        return content;
    }
}

