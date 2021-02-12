package com.webservices.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webservices.fileutils.FileReader;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;

public class FilesHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String getURL = null;
        if ("GET".equals(exchange.getRequestMethod()) || "HEAD".equals(exchange.getRequestMethod())) {
            getURL = formatRequestUri(exchange);
            System.out.println(getURL);
        }
        handleResponse(exchange, getURL);
    }

    private String formatRequestUri(HttpExchange exchange) {
        return exchange.getRequestURI().toString().substring(1);
    }

    private void handleResponse(HttpExchange exchange, String getURL) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();

        File file = new File("files" + File.separator + getURL);
        byte[] page = FileReader.readFromFile(file);

        String content = Files.probeContentType(file.toPath());
        System.out.println(file.getPath());


        if (content == null || content.isEmpty()){
            content = getContentTypeForNotDetected(getURL);
        }
        System.out.println(content);

        exchange.getResponseHeaders().set("Content-Type", content);
        exchange.getResponseHeaders().set("Content-Length", String.valueOf(page.length));
        exchange.sendResponseHeaders(200, page.length);


        if ("GET".equals(exchange.getRequestMethod())) {
            outputStream.write(page);
        }

        outputStream.flush();
        outputStream.close();
    }

    private static String getContentTypeForNotDetected(String getURL) {
        String content = "";
        String fileExtension = StringUtils.substringAfter(getURL, ".").trim();
        if (fileExtension.equals("js")) {
            content = "application/javascript";
        }
        return content;
    }
}

