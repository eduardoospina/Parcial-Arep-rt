package edu.escuelaing.arep;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Clima {

    private Gson gson;

    public Clima(){
        this.gson= new Gson();
    }

    public String Clima(String buscar) throws IOException{
        URL url = new URL ("https://api.openweathermap.org/data/2.5/weather?q="+buscar+"&appid=dc42292589bfac773004653d2a651733");
        HttpURLConnection conectar = (HttpURLConnection) url.openConnection();

        BufferedReader on = new BufferedReader(new InputStreamReader(conectar.getInputStream()));
        String entrada;
        String salida = "";

        while ((entrada = on.readLine()) != null){
            salida += entrada;
        }

        return gson.toJson(salida);
    }
}
