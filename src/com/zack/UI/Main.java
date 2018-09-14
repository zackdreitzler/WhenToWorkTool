package com.zack.UI;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("WhenToWork Tool");
        primaryStage.setScene(new Scene(root, 625, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
