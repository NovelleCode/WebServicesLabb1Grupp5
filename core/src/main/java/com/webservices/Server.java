package com.webservices;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {

        int portNumber = 8080;

        ExecutorService executorService = Executors.newCachedThreadPool();

        try(ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(() -> handleConnection(socket));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static void handleConnection(Socket socket){
            try (var input = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
                String headerLine = input.readLine();
                if(headerLine != null) {
                    new Server().handleHttpRequest(headerLine);
                }
                // First line of header consists of Method, URL and Protocol version
                // Goal is to extract these and redirect the requests.

                while(true){
                    String headerBody = input.readLine();
                    System.out.println(headerBody);
                    if(headerLine.isEmpty())
                        break;
                }

                var output = new PrintWriter(socket.getOutputStream());
                File file = new File("web\\index.html");
                byte[] page = new Server().readFromFile(file);

                output.println("HTTP/1.1 200 OK");
                output.println("Content-Length:" + page.length);
                output.println("Content-Type:text/html");  //application/json
                output.println("");
                output.print(new String(page));


                output.flush();
                socket.close();

            }catch (IOException e) {
                e.printStackTrace();
        }
    }

    private byte[] readFromFile(File file) {
        byte[] content = new byte[0];
        System.out.println("Does file exist: " + file.exists());
        if (file.exists() && file.canRead()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                content = new byte[(int)file.length()];
                int count = fileInputStream.read(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    private void handleHttpRequest(String headerLine) {
        String[] firstHeaderLine = headerLine.split(" ");
        String method = firstHeaderLine[0];
        String url = firstHeaderLine[1];
        // String protocol = firstHeaderLine[2];

        switch (method) {
            case "HEAD":
                //handleHeadRequest(url);
                break;
            case "GET":
                //handleGetRequest(url);
                break;
            case "POST":
                //handlePostRequest(url);
                break;

        }
    }


}
