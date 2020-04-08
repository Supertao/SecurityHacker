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
        mainUI.setScene(new Scene(root, 800, 475));
        mainUI.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
