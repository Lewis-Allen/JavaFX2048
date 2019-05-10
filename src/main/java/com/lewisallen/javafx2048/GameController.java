package com.lewisallen.javafx2048;

import com.lewisallen.javafx2048.input.InputManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GameController
{
    @FXML
    private Label score;

    @FXML
    private Label highScore;

    @FXML
    private Button reset;

    @FXML
    private GridPane grid;

    @FXML
    private Label gameOver;

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
//        // Create a button for testing purposes.
//        testButton.setOnAction(e ->
//        {
//            model.setTileValue(1,0,0);
//            System.out.println(model.getTileValue(0,0));
//            model.setTileValue(2, 1, 0);
//            model.setTileValue(3, 2, 0);
//            model.setTileValue(4, 3, 0);
//            model.setScore(model.getScore() + 1);
//        });

        // Reset Button
        reset.setOnAction((e) -> model.resetGame());

        // Bind scores
        score.textProperty().bind(model.getScoreProperty().asString());
        highScore.textProperty().bind(model.getHighScoreProperty().asString());

        // Bind game over
        gameOver.textProperty().bind(model.isGameOverProperty().asString());

        Font arial = new Font("Arial", 30);

        // Add in values to map to array.
        for(int y = 0; y < 4; y++)
        {
            for(int x = 0; x < 4; x++)
            {
                Label label = new Label();
                label.setFont(arial);
                label.textProperty().bind(model.getTileProperty(x,y).asString());
                grid.add(label, x, y);
            }
        }
    }

    public void setupInputs(Scene scene)
    {
        scene.setOnKeyPressed(key -> {
            KeyCode keyCode = key.getCode();
            inputManager.keyPressed(keyCode);
            if (inputManager.isPressed(KeyCode.UP))
            {
                model.move(Move.UP);
                return;
            }
            if (inputManager.isPressed(KeyCode.RIGHT))
            {
                model.move(Move.RIGHT);
                return;
            }
            if (inputManager.isPressed(KeyCode.DOWN)) {
                model.move(Move.DOWN);
                return;
            }
            if (inputManager.isPressed(KeyCode.LEFT)) {
                model.move(Move.LEFT);
                return;
            }
        });

        scene.setOnKeyReleased(key -> inputManager.keyReleased(key.getCode()));
    }
}
