package com.lewisallen.javafx2048;

import com.lewisallen.javafx2048.input.InputManager;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameController
{
    @FXML
    private VBox container;

    @FXML
    private Button testButton;

    @FXML
    private Label score;

    @FXML
    private Button reset;

    private GameModel model;
    private InputManager inputManager;

    public GameController()
    {
        inputManager = new InputManager();
        model = new GameModel();
    }

    @FXML
    public void initialize()
    {
        // Create a button for testing purposes.
        testButton.setOnAction(e ->
        {
            model.setTileValue(1,0,0);
            System.out.println(model.getTileValue(0,0));
            model.setTileValue(2, 1, 0);
            model.setTileValue(3, 2, 0);
            model.setTileValue(4, 3, 0);
            model.setScore(model.getScore() + 1);
        });

        // Reset Button
        reset.setOnAction((e) -> model.resetGame());

        // Bind score
        score.textProperty().bind(model.getScoreProperty().asString());

        // Add in values to map to array.
        for(int y = 0; y < 4; y++)
        {
            HBox hbox = new HBox();
            for(int x = 0; x < 4; x++)
            {
                Label label = new Label();
                label.textProperty().bind(model.getTileProperty(x,y).asString());
                hbox.getChildren().add(label);
            }
            container.getChildren().add(hbox);
        }
    }

    public void setupInputs(Scene scene)
    {
        scene.setOnKeyPressed(key -> {
            KeyCode keyCode = key.getCode();
            inputManager.keyPressed(keyCode);
            if (inputManager.isPressed(KeyCode.UP))
            {
                model.moveUp();
                return;
            }
            if (inputManager.isPressed(KeyCode.RIGHT))
            {
                model.moveRight();
                return;
            }
            if (inputManager.isPressed(KeyCode.DOWN)) {
                model.moveDown();
                return;
            }
            if (inputManager.isPressed(KeyCode.LEFT)) {
                model.moveLeft();
                return;
            }
        });

        scene.setOnKeyReleased(key -> inputManager.keyReleased(key.getCode()));
    }
}
