package com.project.aBabinskiy.client;

import com.project.aBabinskiy.data.Account;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/project/aBabinskiy/client/windows/profiles/sample.fxml"));
        primaryStage.setTitle("Hotel");
        primaryStage.setScene(new Scene(root, 1000,800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
