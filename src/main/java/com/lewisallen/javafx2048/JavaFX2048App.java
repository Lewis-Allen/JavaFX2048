package com.lewisallen.javafx2048;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFX2048App extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("JavaFX2048.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 430, 540);

        GameController controller = loader.getController();
        controller.setupInputs(scene);

        stage.setTitle("2048");
        stage.setScene(scene);
        stage.show();
    }
}
