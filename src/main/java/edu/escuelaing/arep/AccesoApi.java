package edu.escuelaing.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class AccesoApi {

    public static String consultarclima(String city) throws MalformedURLException {

        URL url = new URL("http://api.openweathermap.org/data/2.5/find?q="+city+"&appid=dc42292589bfac773004653d2a651733");
        String resultado = "";
        try (BufferedReader Lector = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine = null;
            while ((inputLine = Lector.readLine()) != null) {
                resultado = resultado + inputLine;
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return resultado;
    }
}