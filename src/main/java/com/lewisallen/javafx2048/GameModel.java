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

    /**
     * Sets score to zero, resets board and generates two random tiles.
     */
    public void resetGame()
    {
        setScore(0);
        Arrays.stream(tiles).forEach((y) -> Arrays.stream(y).forEach((x) -> x.setValue(0)));
        createNewTile(tiles);
        createNewTile(tiles);
    }

    public void move(Move move)
    {
        System.out.println("Moving " + move);

        boolean moved = false; // Did anything actually move on input.

        switch(move)
        {
            case LEFT:
                for(int y = 0; y < tiles.length; y++)
                {
                    for(int x = 0; x < tiles[y].length - 1; x++)
                    {
                        for(int z = x + 1; z < tiles[y].length; z++)
                        {
                            if(tiles[y][x].get() != 0 && tiles[y][z].get() != 0 && tiles[y][x].get() != tiles[y][z].get())
                            {
                                break;
                            }

                            if(tiles[y][x].get() == 0 && tiles[y][z].get() != 0)
                            {
                                tiles[y][x].set(tiles[y][z].get());
                                tiles[y][z].set(0);
                                moved = true;
                            }

                            if(tiles[y][x].get() == tiles[y][z].get() && tiles[y][x].get() != 0)
                            {
                                tiles[y][x].set(tiles[y][x].get() + tiles[y][z].get());
                                tiles[y][z].set(0);
                                score.set(score.getValue() + tiles[y][x].get());
                                moved = true;
                                break;
                            }
                        }
                    }
                }
                break;

            case RIGHT:
                // This matches the LEFT logic exactly, just different loop counters.
                // ToDo: Breakout into method.
                for(int y = 0; y < tiles.length; y++)
                {
                    for(int x = tiles.length - 1; x > 0; x--)
                    {
                        for(int z = x - 1; z >= 0; z--)
                        {
                            if(tiles[y][x].get() != 0 && tiles[y][z].get() != 0 && tiles[y][x].get() != tiles[y][z].get())
                            {
                                break;
                            }

                            if(tiles[y][x].get() == 0 && tiles[y][z].get() != 0)
                            {
                                tiles[y][x].set(tiles[y][z].get());
                                tiles[y][z].set(0);
                                moved = true;
                            }

                            if(tiles[y][x].get() == tiles[y][z].get() && tiles[y][x].get() != 0)
                            {
                                tiles[y][x].set(tiles[y][x].get() + tiles[y][z].get());
                                tiles[y][z].set(0);
                                score.set(score.getValue() + tiles[y][x].get());
                                moved = true;
                                break;
                            }
                        }
                    }
                }
                break;

            case DOWN:
                // ToDo: Transpose matrix then move accordingly.
                break;

            case UP:
                // ToDo: See above.
                break;
        }

        if(moved)
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

    /**
     * Gets a random value for a new tile.
     * @return value for tile.
     */
    private int getNewTileValue()
    {
        Random r = new Random();
        return r.nextInt(100) < 10 ? 4 : 2;
    }

    /**
     * Check whether the game is over.
     */
    private void checkForLoss()
    {
    }
}
