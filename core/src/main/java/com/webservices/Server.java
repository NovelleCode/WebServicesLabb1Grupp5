package com.webservices;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
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
            try{
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while(true){
                    String headerLine = input.readLine();
                    System.out.println(headerLine);
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
        System.out.println("Does file exists: " + file.exists());
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
}
