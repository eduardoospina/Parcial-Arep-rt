package edu.escuelaing.arep;

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
                        + "<input type='text' class='form-control' placeholder='Clima' name='clima' id='clima' style=margin-bottom: 10px>"
                        + "<input id='button-Clima' type='submit' value='Buscar' onclick='ClimasE()' >"
                        + "<p  id='climasl' ><b></b></p>"
                        + "<script>"
                        + "const urls = 'https://climaarep-p.herokuapp.com/consulta?lugar=';"
                        + "const valor = document.getElementById('clima');"
                        + "const resultado = document.getElementById('climasl');"
                        + "async function ClimasE(){ \n"
                        + "const ans = await GetData(valor.value);"
                        + "const res = JSON.stringify(ans);"
                        + " resultado.innerHTML = `Resultado: ${res}`;"
                        + "}"
                        + "async function GetData(valor){  \n"
                        + "const urlconseguir = `${urls}${valor}`;"
                        + "const rest = await fetch(urlconseguir, { \n"
                        + " method: 'GET',\n"
                        + "headers: {\n"
                        + "'Content-Type': 'application/json'"
                        + "}"
                        + "});"
                        + "console.log('res', res);\n"
                        + "const resultados1 = await res.json();\n"
                        + "console.log('resultados1', resultados1);\n"
                        + "return resultados1;\n"
                        + "}"
                        + "</script>"
                        + "</body>"
                        + "</html>" + inputLine;

            }else if (file.contains("/consulta?lugar=")){
                filesplit = file.split("=");
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: application/json\r\n"
                        + "\r\n"
                        + AccesoApi.consultarclima(filesplit[1]);
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