package com.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args)  {
        int portNumber = 8080;
        try(Socket socket = new Socket("localhost",portNumber)){
            var output = new PrintWriter(socket.getOutputStream());
            output.flush();
            var input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(input.readLine());

            output.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
