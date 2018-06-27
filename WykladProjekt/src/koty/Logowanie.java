

package koty;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import javax.swing.*;
import java.beans.Visibility;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Logowanie extends Application implements EventHandler<ActionEvent>{

    TextField textField;
    TextField textField2;
    Button buttonAdd;
    Stage primaryStage;
    String token;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;
        primaryStage.setTitle("Logowanie");
        primaryStage.show();
        VBox layout = new VBox();
        Label label1 = new Label("Email:");
        layout.getChildren().add(label1);

        textField= new TextField();
        layout.getChildren().add(textField);

        Label label2 = new Label("Password:");
        layout.getChildren().add(label2);

        textField2= new TextField();
        layout.getChildren().add(textField2);

        buttonAdd = new Button("LOG IN");
        buttonAdd.setOnAction(this);
        HBox h = new HBox();
        h.getChildren().add(buttonAdd);
        h.setAlignment(Pos.CENTER);
        layout.getChildren().add(h);


        layout.setSpacing(10);
        layout.setPadding(new Insets(20,30,20,30));

        Scene scene = new Scene(layout,400,300);

        primaryStage.setScene(scene);


    }



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource().equals(buttonAdd)){

            try {
                URL url = new URL("http://smieszne-koty.herokuapp.com/oauth/token?grant_type=password&email="+textField.getText()+"&password="+textField2.getText());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setReadTimeout(30000);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = input.readLine()) != null)
                    response.append(inputLine);
                input.close();
                System.out.println(response);
                JSONObject autoryzacja = new JSONObject(response.toString());
                token = autoryzacja.getString("access_token");
                Stage pS = new Stage();
                OknoKotow admin = new OknoKotow();
                admin.setToken(token);
                admin.start(pS);
                primaryStage.close();


            }catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Niepoprawne dane");

                alert.showAndWait();

            }



        }
    }
}