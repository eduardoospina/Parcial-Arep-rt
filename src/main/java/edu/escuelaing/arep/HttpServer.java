package edu.escuelaing.arep;

import javax.net.ssl.HttpsURLConnection;
import java.net.*;
import java.io.*;


public class HttpServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;

    public static void main(String[] args) throws Exception {
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
            String inputLine, outputLine = null;
            String file = "";
            String[] filesplit ;
            boolean primeraLinea = true;
            while ((inputLine = in.readLine()) != null) {
                if (primeraLinea) {
                    file = inputLine.split(" ")[1];
                    primeraLinea = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            System.out.println(file);
            //Siempre responde la misma página
            if(file.startsWith("/Clima")) {
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

            }else if (file.contains("/consulta?lugar=")){
                filesplit = file.split("=");
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: application/json\r\n"
                        + "\r\n"
                        + Clima.consultarclima(filesplit[1]);
            }else{
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text /html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>web page</title>\n"
                        + "</head>"
                        + "<body>"
                        + "<h1> Recurso web para identificar el clima actual dependiendo de la ciudad desea </h1>"
                        + "<h2> Si desea buscar el clima de una ciudad especifica porfavor ingrese a: https://climaarep-p.herokuapp.com/Clima</h2>"
                        + "<h2> Si por el contrario desea utilizar directamente al API porfavor ingrese a, ingresando la ciudad que desea directamente: https://climaarep-p.herokuapp.com/consulta?lugar='ciudad desea a conocer clima'</h2>"
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

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)
    }

}