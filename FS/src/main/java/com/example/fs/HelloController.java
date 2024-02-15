package com.example.fs;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class HelloController {
    @FXML
    private TextField username_signin;

    @FXML
    private TextField password_signin;

    @FXML
    private Label missingCredentials;


    public PostgresqlDataBase db = new PostgresqlDataBase();

    public void LogIn(Event e){

        if (username_signin.getText().isBlank() && password_signin.getText().isEmpty()){
            emptyCases("Missing Entire Credentials!");
        } else if(username_signin.getText().isBlank()){
            emptyCases("Missing username!");

        }else if(password_signin.getText().isEmpty()){
            emptyCases("Missing password!");
        }else{

            db.writeDatabase("JonathanGwapo@gmail.com", username_signin.getText(), password_signin.getText());
        }

    }
    public void emptyCases(String missing){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> {
            missingCredentials.setText(missing);

            Timeline revertTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), el ->{
                missingCredentials.setText("");
            }));

            revertTimeline.play();
        }));

        timeline.play();
    }
}