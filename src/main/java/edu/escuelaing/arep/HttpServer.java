package edu.escuelaing.arep;

import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import java.net.*;
import java.io.*;
import java.util.*;


public class HttpServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private static Gson gson;


    public static void main(String[] args) throws Exception {
         Gson gson = new Gson();

        ServerSocket serverSocket = null;
        boolean running = true;

        try {
            serverSocket = new ServerSocket(getPort()); //sOCKET DE SERVIDOR, PUERTO 35000
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
            String file = "";
            boolean primeraLinea = true;
            String[] fsplits ;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (primeraLinea) {
                    file = inputLine.split(" ")[1];
                    System.out.println("File: " + file);
                    primeraLinea = false;
                }
                if (!in.ready()) {
                    break;
                }
            }

            if(file.startsWith("/Clima")){
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
                        + "<h1>  Ciudad a buscar</h1>"
                        + "<form"
                        + "<input type=\"text\" class=\"form-control\" placeholder=\"Clima\" id=\"Clima\" style=\"margin-bottom: 10px\">"
                        + "<input id=\"button-Clima\" type=\"submit\" value=\"Buscar\" class=\"btns\" >"
                        + "<h4  id=\"climasl\"></h4>"
                        + "</body>"
                        + "</html>" + inputLine;
            }
            else if (file.contains("/Consultas?lugar=")){
                fsplits = file.split("=");
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text /html\r\n"
                        + "\r\n";
                outputLine += CreandoConexionApi(fsplits[1]);

            }
            else{
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
            }

                out.println(outputLine);
                out.close();
                in.close();
                clientSocket.close();
            }
        serverSocket.close();
    }

    public static String CreandoConexionApi(String Ciudad) throws IOException {

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + Ciudad +  "&appid=dc42292589bfac773004653d2a65173";
        URL urlClima = new URL(url);
        HttpsURLConnection conectarUrl = (HttpsURLConnection)urlClima.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conectarUrl.getInputStream()));
        String input, output = "";
        while ((input = in.readLine()) != null){
            output += input;
        }
        return gson.toJson(output);
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)
    }

}

