package com.lewisallen.javafx2048;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameModel
{
    private IntegerProperty score;
    private IntegerProperty highScore;
    private IntegerProperty[][] tiles;
    private BooleanProperty isGameOver;

    public GameModel()
    {
        isGameOver = new SimpleBooleanProperty(false);

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
        highScore = new SimpleIntegerProperty(0);
    }

    public IntegerProperty getTileProperty(int x, int y)
    {
        return tiles[y][x];
    }

    public void setScore(int score)
    {
        this.score.setValue(score);
        if(score > highScore.get())
        {
            highScore.setValue(score);
        }
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
                                setScore(score.getValue() + tiles[y][x].get());
                                moved = true;
                                break;
                            }
                        }
                    }
                }
                break;

            case RIGHT:
                // This matches the LEFT logic exactly, just different loop counters.
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
                                setScore(score.getValue() + tiles[y][x].get());
                                moved = true;
                                break;
                            }
                        }
                    }
                }
                break;

            case DOWN:
                IntegerProperty[][] tilesTemp = transpose(this.tiles);

                for(int y = 0; y < tilesTemp.length; y++)
                {
                    for(int x = tilesTemp.length - 1; x > 0; x--)
                    {
                        for(int z = x - 1; z >= 0; z--)
                        {
                            if(tilesTemp[y][x].get() != 0 && tilesTemp[y][z].get() != 0 && tilesTemp[y][x].get() != tilesTemp[y][z].get())
                            {
                                break;
                            }

                            if(tilesTemp[y][x].get() == 0 && tilesTemp[y][z].get() != 0)
                            {
                                tilesTemp[y][x].set(tilesTemp[y][z].get());
                                tilesTemp[y][z].set(0);
                                moved = true;
                            }

                            if(tilesTemp[y][x].get() == tilesTemp[y][z].get() && tilesTemp[y][x].get() != 0)
                            {
                                tilesTemp[y][x].set(tilesTemp[y][x].get() + tilesTemp[y][z].get());
                                tilesTemp[y][z].set(0);
                                setScore(score.getValue() + tilesTemp[y][x].get());
                                moved = true;
                                break;
                            }
                        }
                    }
                }

                this.tiles = transpose(tilesTemp);
                break;

            case UP:
                IntegerProperty[][] tilesTemp2 = transpose(this.tiles);

                for(int y = 0; y < tilesTemp2.length; y++)
                {
                    for(int x = 0; x < tilesTemp2[y].length - 1; x++)
                    {
                        for(int z = x + 1; z < tilesTemp2[y].length; z++)
                        {
                            if(tilesTemp2[y][x].get() != 0 && tilesTemp2[y][z].get() != 0 && tilesTemp2[y][x].get() != tilesTemp2[y][z].get())
                            {
                                break;
                            }

                            if(tilesTemp2[y][x].get() == 0 && tilesTemp2[y][z].get() != 0)
                            {
                                tilesTemp2[y][x].set(tilesTemp2[y][z].get());
                                tilesTemp2[y][z].set(0);
                                moved = true;
                            }

                            if(tilesTemp2[y][x].get() == tilesTemp2[y][z].get() && tilesTemp2[y][x].get() != 0)
                            {
                                tilesTemp2[y][x].set(tilesTemp2[y][x].get() + tilesTemp2[y][z].get());
                                tilesTemp2[y][z].set(0);
                                setScore(score.getValue() + tilesTemp2[y][x].get());
                                moved = true;
                                break;
                            }
                        }
                    }
                }

                this.tiles = transpose(tilesTemp2);
                break;
        }

        if(moved)
            createNewTile(tiles);

        isGameOver.setValue(checkForLoss());
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
    private boolean checkForLoss()
    {
        // Check if there are any zeros,
        for(IntegerProperty[] row : tiles)
        {
            // Check if there are any zeros. If there are then the game continues.
            boolean containsZeros = Arrays.stream(row).anyMatch(value -> value.get() == 0);
            if(containsZeros)
                return false;
        }

        // if not, check if a value matches any values around it.
        IntegerProperty[][] transposed = transpose(tiles);
        for(int i = 0; i < tiles.length; i++)
        {
            if(hasMatchingAdjacentIntegers(tiles[i]) || hasMatchingAdjacentIntegers(transposed[i]))
                return false;
        }

        // Otherwise we cannot move and true true;
        return true;
    }

    private IntegerProperty[][] transpose(IntegerProperty[][] original)
    {
        IntegerProperty[][] transposed = new SimpleIntegerProperty[original.length][original[0].length];

        for (int i = 0; i < original.length; i++)
        {
            for (int j = 0; j < original[0].length; j++)
            {
                transposed[j][i] = original[i][j];
            }
        }

        return transposed;
    }

    public int getHighScore()
    {
        return highScore.get();
    }

    public IntegerProperty getHighScoreProperty()
    {
        return highScore;
    }

    public boolean isIsGameOver()
    {
        return isGameOver.get();
    }

    public BooleanProperty isGameOverProperty()
    {
        return isGameOver;
    }

    public boolean hasMatchingAdjacentIntegers(IntegerProperty[] row)
    {
        for(int i = 0; i < row.length - 1; i++)
        {
            if(row[i].get() == row[i+1].get())
                return true;
        }

        return false;
    }
}
