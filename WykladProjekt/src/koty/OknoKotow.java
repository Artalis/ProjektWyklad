


        package koty;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class OknoKotow extends Application implements EventHandler<ActionEvent> {



    Button nastepny;
    Stage primaryStage;
    Label txtA;
    Label txt2;
    JSONArray kotki;
    int counter=0;
    int strona=1;
    ImageView imageView;
    String token;
    URL url2;
    Image image;
    String path;

    public void setToken(String s){
        token=s;
    }

    public void ustawKotełka (){
        JSONObject kotek=((JSONArray) kotki).getJSONObject(counter);
        path =((JSONObject) kotek).getString("url");
        image = new Image(path);
        imageView = new ImageView(image);
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);
        txtA.setText(kotek.getString("name"));
        txt2.setText("Na tego kotka zagłosowało: "+kotek.getInt("vote_count")+" osób");
    }

    public void zwracanieKotelkow(int page){
        try {

            url2 = new URL("http://smieszne-koty.herokuapp.com/api/kittens?access_token="+token+"&page="+page);
            HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(30000);

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine =input.readLine())!=null)
                response.append(inputLine);
            input.close();
            kotki = new JSONArray(response.toString());

            ustawKotełka();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onStage(){
        primaryStage.setTitle("Twoje Koteły");
        primaryStage.show();



        nastepny = new Button();
        nastepny.setPrefWidth(200);
        nastepny.setOnAction(this);
        nastepny.setText("Następny Kotełek");
        txtA.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,14));
        txt2.setFont(Font.font("TimesNewRoman",FontWeight.BOLD,14));
        HBox h = new HBox(txtA);
        h.setAlignment(Pos.CENTER);
        HBox h2 = new HBox(txt2);
        h2.setAlignment(Pos.CENTER);

        VBox v = new VBox(h,imageView,h2,nastepny);
        v.setSpacing(10);
        v.setAlignment(Pos.CENTER);
        v.setSpacing(10);
        Scene scene = new Scene(v,400,400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;
        txt2 = new Label();
        txtA = new Label();
        zwracanieKotelkow(1);
        counter++;
        txtA.setAlignment(Pos.CENTER);
        onStage();






    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource().equals(nastepny)){
            if(counter<kotki.length()) {
                counter++;
                ustawKotełka();
                onStage();
                nastepny.setText("Następny Kotełek");

                if(counter==(kotki.length()-1)){
                    nastepny.setText("Następna strona z Kotełkami");
                    nastepny.setDisable(true);
                    counter=0;
                    strona++;
                    zwracanieKotelkow(strona);
                    onStage();
                    nastepny.setDisable(false);
                }
            }


        }
    }
}
