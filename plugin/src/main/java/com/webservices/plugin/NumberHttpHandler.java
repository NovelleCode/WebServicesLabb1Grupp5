package com.webservices.plugin;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webservices.annotations.Route;
import com.webservices.constants.NameConstants;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Route("/number")
public class NumberHttpHandler implements HttpHandler {

        int numberOfTimes = 0;

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            numberOfTimes++;
            OutputStream outputStream = exchange.getResponseBody();
            List<String> userAgent = exchange.getRequestHeaders().get("User-Agent");
            String page2 = """
                <html>
                <head>
                    <title>Easteregg</title>
                </head>
                <body>
                <h1>You've been here %s times </h1> 
                <p> you are reaching us via: %s</p>
                </body>
                </html>""".formatted(numberOfTimes, userAgent);

            exchange.getResponseHeaders().set(NameConstants.CONTENTTYPE, NameConstants.CONTENTTYPE_TEXT_HTML);

            exchange.getResponseHeaders().set(NameConstants.CONTENTLENGTH, String.valueOf(page2.length()));
            exchange.sendResponseHeaders(200, page2.length());

            outputStream.write(page2.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        }
}

