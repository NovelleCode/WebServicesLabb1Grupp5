package com.webservices.plugin;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

@Route("/easteregg")
public class PluginHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "HEAD":
                handleHeaderResponse(exchange);
                break;
            case "GET":
                handleHeaderResponse(exchange);
                //handleBodyResponse(exchange);
                // Load some funny image or some javascript function
                break;
            default:
                System.out.println("Konstig request");
                System.out.println(exchange.getRequestMethod());
                break;
        }
    }

    private void handleHeaderResponse(HttpExchange exchange) throws IOException {
        File file = new File("files" + File.separator + "easteregg");
        String contentType = Files.probeContentType(file.toPath());
        exchange.getResponseHeaders().set("Content-Type: ", contentType);
        exchange.getResponseHeaders().set("Content-Length: ", String.valueOf(file.length()));
        exchange.sendResponseHeaders(200, file.length());
    }

    private void handleBodyResponse(HttpExchange exchange) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();

        File file = new File("files" + File.separator + "easteregg");;
//        byte[] page = FileReader.readFromFile(file);

//        outputStream.write(page);
        outputStream.flush();
        outputStream.close();
    }
}
