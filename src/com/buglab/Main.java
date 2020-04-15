package com.buglab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private final  static String title="SecurityHacker";


    @Override

    public void start(Stage mainUI) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("configure.fxml"));
        mainUI.setTitle(title);
        Scene scene = new Scene(root, 800, 450);
        scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        mainUI.setScene(scene);
        mainUI.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
