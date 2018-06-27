package koty;


import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JavaKot {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://smieszne-koty.herokuapp.com/oauth/token?grant_type=password&email=qwertyuiop@gmail.com&password=asdfghjkl");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(30000);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine =input.readLine())!=null)
                response.append(inputLine);
            input.close();
            System.out.println(response);
            JSONObject autoryzacja = new JSONObject(response.toString());
            System.out.println("Token= "+autoryzacja.getString("access_token"));
            String token=autoryzacja.getString("access_token");
            URL url2 = new URL("http://smieszne-koty.herokuapp.com/api/kittens?access_token="+token);
            connection = (HttpURLConnection) url2.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(30000);

            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            response = new StringBuilder();

            while ((inputLine =input.readLine())!=null)
                response.append(inputLine);
            input.close();
            System.out.println(response);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
