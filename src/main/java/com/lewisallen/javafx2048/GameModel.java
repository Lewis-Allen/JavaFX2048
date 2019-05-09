package com.lewisallen.javafx2048;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameModel
{
    private IntegerProperty score;
    private IntegerProperty[][] tiles;

    public GameModel()
    {
        IntegerProperty[][] ints = new IntegerProperty[4][4];
        for(int y = 0; y < 4; y++)
        {
            for(int x = 0; x < 4; x++)
            {
                ints[y][x] = new SimpleIntegerProperty();
            }
        }
        tiles = ints;

        createNewTile(tiles);
        createNewTile(tiles);

        score = new SimpleIntegerProperty(0);
    }

    public void setTileValue(int value, int x, int y)
    {
        tiles[y][x].set(value);
    }

    public int getTileValue(int x, int y)
    {
        return tiles[y][x].get();
    }

    public IntegerProperty getTileProperty(int x, int y)
    {
        return tiles[y][x];
    }

    public int getScore()
    {
        return score.get();
    }

    public void setScore(int score)
    {
        this.score.setValue(score);
    }

    public IntegerProperty getScoreProperty()
    {
        return score;
    }

    public void resetGame()
    {
        setScore(0);
        Arrays.stream(tiles).forEach((y) -> Arrays.stream(y).forEach((x) -> x.setValue(0)));
        createNewTile(tiles);
        createNewTile(tiles);
    }

    /**
     * Randomly generates a new tile on the board.
     */
    public void createNewTile(IntegerProperty[][] tiles)
    {
        List<IntegerProperty> tilesList = new ArrayList<>();

        for (IntegerProperty[] e : tiles)
        {
            tilesList.addAll(Arrays.asList(e));
        }

        tilesList = tilesList.stream()
                .filter(tile -> tile.get() == 0)
                .collect(Collectors.toList());

        Random r = new Random();

        IntegerProperty tile = tilesList.get(r.nextInt(tilesList.size()));
        tile.set(getNewTileValue());
    }

    private int getNewTileValue()
    {
        Random r = new Random();
        return r.nextInt(100) < 10 ? 4 : 2;
    }

    public void moveUp()
    {
        System.out.println("Moving" + new Throwable().getStackTrace()[0].getMethodName());
    }

    public void moveRight()
    {
        System.out.println("Moving" + new Throwable().getStackTrace()[0].getMethodName());
    }

    public void moveDown()
    {
        System.out.println("Moving" + new Throwable().getStackTrace()[0].getMethodName());
    }

    public void moveLeft()
    {
        System.out.println("Moving" + new Throwable().getStackTrace()[0].getMethodName());
    }

    private void checkForLoss()
    {

    }
}
