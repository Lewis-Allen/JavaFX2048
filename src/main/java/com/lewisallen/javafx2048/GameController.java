package com.lewisallen.javafx2048;

import com.lewisallen.javafx2048.input.InputManager;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

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

    private GameModel model = new GameModel();
    private InputManager inputManager = new InputManager();

    @FXML
    public void initialize()
    {
        // Reset Button
        reset.setOnAction((e) -> model.resetGame());

        // Bind scores
        score.textProperty().bind(model.scoreProperty().asString());
        highScore.textProperty().bind(model.highScoreProperty().asString());

        // Bind game over
        gameOver.textProperty().bind(Bindings.when(model.gameOverProperty())
                .then("Game Over")
                .otherwise(""));

        // Add in values to map to array.
        for (int y = 0; y < 4; y++)
        {
            for (int x = 0; x < 4; x++)
            {
                grid.add(model.getTile(x, y), x, y);
            }
        }
    }

    /**
     * Setup inputs for the game.
     *
     * @param scene The scene the game is drawn on.
     */
    public void setupInputs(Scene scene)
    {
        scene.setOnKeyPressed(key ->
        {
            KeyCode keyCode = key.getCode();
            inputManager.keyPressed(keyCode);
            if (inputManager.isPressed(KeyCode.UP))
            {
                model.move(Move.UP);
            }
            else if (inputManager.isPressed(KeyCode.RIGHT))
            {
                model.move(Move.RIGHT);
            }
            else if (inputManager.isPressed(KeyCode.DOWN))
            {
                model.move(Move.DOWN);
            }
            else if (inputManager.isPressed(KeyCode.LEFT))
            {
                model.move(Move.LEFT);
            }
        });

        scene.setOnKeyReleased(key -> inputManager.keyReleased(key.getCode()));
    }
}
