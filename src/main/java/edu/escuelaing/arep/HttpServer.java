package edu.escuelaing.arep;

import java.net.*;
import java.io.*;
import java.util.*;


public class HttpServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;


    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = null;
        boolean running = true;

        try {
            serverSocket = new ServerSocket(35001); //sOCKET DE SERVIDOR, PUERTO 35000
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        while (running) {

            Socket clientSocket = null; //Cliente socket
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept(); //Se pone a aceptar conexiones
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); //Flujo de salida
            BufferedReader in = new BufferedReader( //Flujo de entrada
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            //Siempre responde la misma página

                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text /html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Title of the document</title>\n"
                        + "</head>"
                        + "<body>"
                        + "Web Page"
                        + "</body>"
                        + "</html>" + inputLine;
                out.println(outputLine);
                out.close();
                in.close();
                clientSocket.close();
            }
        serverSocket.close();
    }

}

