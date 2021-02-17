package com.webservices.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webservices.fileutils.FileReader;
import com.webservices.annotations.Route;
import com.webservices.constants.NameConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

@Route("/")
public class FilesHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

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
        int rCode = 404;

        if (file.exists()) {
            rCode = 200;
            String contentType = Files.probeContentType(file.toPath());
//            Files.probeContentType cannot identify JavaScript files
//            getContentTypeForNotDetected() checks for JavaScript files and gives it correct contentType
            if (contentType == null || contentType.isEmpty()) {
                contentType = getContentTypeForNotDetected(formatRequestUri(exchange));
            }
            exchange.getResponseHeaders().set(NameConstants.CONTENTTYPE, contentType);
        }
            exchange.getResponseHeaders().set(NameConstants.CONTENTLENGTH, String.valueOf(file.length()));
            exchange.sendResponseHeaders(rCode, file.length());
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

