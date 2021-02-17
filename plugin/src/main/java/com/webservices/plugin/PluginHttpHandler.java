package com.webservices.plugin;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webservices.annotations.Route;
import com.webservices.constants.NameConstants;
import com.webservices.fileutils.FileReader;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

@Route("/easteregg")
public class PluginHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        File file = new File(NameConstants.FILES + File.separator + "easteregg/easteregg.html");
        System.out.println("Plugg");

        switch(exchange.getRequestMethod()) {
            case "HEAD":
                handleHeaderResponse(exchange, file);
                break;
            case "GET":
                handleHeaderResponse(exchange, file);
                handleBodyResponse(exchange, file);
                break;
            default:
                System.out.println("Konstig request");
                System.out.println(exchange.getRequestMethod());
                break;
        }
    }

    private void handleHeaderResponse(HttpExchange exchange, File file) throws IOException {

        String contentType = Files.probeContentType(file.toPath());
        exchange.getResponseHeaders().set(NameConstants.CONTENTTYPE, contentType);

        exchange.getResponseHeaders().set(NameConstants.CONTENTLENGTH, String.valueOf(file.length()));
        exchange.sendResponseHeaders(200, file.length());

    }

    private void handleBodyResponse(HttpExchange exchange, File file) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();

        byte[] page = FileReader.readFromFile(file);

        outputStream.write(page);
        outputStream.flush();
        outputStream.close();
    }
}

