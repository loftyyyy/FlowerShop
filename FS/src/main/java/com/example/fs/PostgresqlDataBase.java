package com.example.fs;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.sql.*;

public class PostgresqlDataBase {

    String url = "jdbc:postgresql://localhost:5435/postgres";
    String dbUsername = "postgres";
    String dbPassword = "admin";

    String insertQuery = "INSERT INTO users(email, username, password) VALUES(?, ?, ?)";
    String retrieveQuery = "SELECT email, username, password FROM users";

    // TODO: Dependency Injection
    @FXML
    private Label missingCredentials;

    public void writeDatabase(String email, String username, String password){
        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement(insertQuery);
            PreparedStatement pst2 = db.prepareStatement(retrieveQuery);
            ResultSet rst = pst2.executeQuery()){


            System.out.println("Database connected!");
            boolean isDuplicate = false;
            while(rst.next()){

                if(rst.getString("email").equals(email) || rst.getString("username").equals(username)){
                    isDuplicate = true;
                    break;
                }
            }

            if(isDuplicate){
                //this.emptyCases("Email or Username Already Registered");
                System.out.println("Email or Username Already Registered");

            }else {
                pst.setString(1, email);
                pst.setString(2, username);
                pst.setString(3, password);
                pst.executeUpdate();
                this.emptyCases("Successfully Created User!");
            }
            this.readDatabase("test", "test", "test");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private void readDatabase(String email, String username, String password){
        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement(retrieveQuery);
            ResultSet rst = pst.executeQuery()){
            while(rst.next()){
                String retrieveEmail = rst.getString("email");
                String retrieveUsername = rst.getString("username");
                String retievePassword = rst.getString("password");

                System.out.println(retrieveEmail);
                System.out.println(retrieveUsername);
                System.out.println(retievePassword);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
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
