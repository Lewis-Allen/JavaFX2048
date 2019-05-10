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
    private Tile[][] tiles;
    private BooleanProperty isGameOver;

    public GameModel()
    {
        isGameOver = new SimpleBooleanProperty(false);

        Tile[][] tiles = new Tile[4][4];
        for (int y = 0; y < 4; y++)
        {
            for (int x = 0; x < 4; x++)
            {
                tiles[y][x] = new Tile(100, 100);
            }
        }
        this.tiles = tiles;

        createNewTile(this.tiles);
        createNewTile(this.tiles);

        score = new SimpleIntegerProperty(0);
        highScore = new SimpleIntegerProperty(0);
    }

    public Tile getTile(int x, int y)
    {
        return tiles[y][x];
    }

    public void setScore(int score)
    {
        this.score.setValue(score);
        if (score > highScore.get())
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
        isGameOver.setValue(false);
        Arrays.stream(tiles).forEach((y) -> Arrays.stream(y).forEach((x) -> x.setValue(0)));
        createNewTile(tiles);
        createNewTile(tiles);
    }

    public void move(Move move)
    {
        System.out.println("Moving " + move);

        boolean moved = false; // Did anything actually move on input.

        switch (move)
        {
            case LEFT:
                for (int y = 0; y < tiles.length; y++)
                {
                    for (int x = 0; x < tiles[y].length - 1; x++)
                    {
                        for (int z = x + 1; z < tiles[y].length; z++)
                        {
                            if (tiles[y][x].getValue() != 0 && tiles[y][z].getValue() != 0 && tiles[y][x].getValue() != tiles[y][z].getValue())
                            {
                                break;
                            }

                            if (tiles[y][x].getValue() == 0 && tiles[y][z].getValue() != 0)
                            {
                                tiles[y][x].setValue(tiles[y][z].getValue());
                                tiles[y][z].setValue(0);
                                moved = true;
                            }

                            if (tiles[y][x].getValue() == tiles[y][z].getValue() && tiles[y][x].getValue() != 0)
                            {
                                tiles[y][x].setValue(tiles[y][x].getValue() + tiles[y][z].getValue());
                                tiles[y][z].setValue(0);
                                setScore(score.getValue() + tiles[y][x].getValue());
                                moved = true;
                                break;
                            }
                        }
                    }
                }
                break;

            case RIGHT:
                // This matches the LEFT logic exactly, just different loop counters.
                for (int y = 0; y < tiles.length; y++)
                {
                    for (int x = tiles.length - 1; x > 0; x--)
                    {
                        for (int z = x - 1; z >= 0; z--)
                        {
                            if (tiles[y][x].getValue() != 0 && tiles[y][z].getValue() != 0 && tiles[y][x].getValue() != tiles[y][z].getValue())
                            {
                                break;
                            }

                            if (tiles[y][x].getValue() == 0 && tiles[y][z].getValue() != 0)
                            {
                                tiles[y][x].setValue(tiles[y][z].getValue());
                                tiles[y][z].setValue(0);
                                moved = true;
                            }

                            if (tiles[y][x].getValue() == tiles[y][z].getValue() && tiles[y][x].getValue() != 0)
                            {
                                tiles[y][x].setValue(tiles[y][x].getValue() + tiles[y][z].getValue());
                                tiles[y][z].setValue(0);
                                setScore(score.getValue() + tiles[y][x].getValue());
                                moved = true;
                                break;
                            }
                        }
                    }
                }
                break;

            case DOWN:
                Tile[][] tilesTemp = transpose(this.tiles);

                for (int y = 0; y < tilesTemp.length; y++)
                {
                    for (int x = tilesTemp.length - 1; x > 0; x--)
                    {
                        for (int z = x - 1; z >= 0; z--)
                        {
                            if (tilesTemp[y][x].getValue() != 0 && tilesTemp[y][z].getValue() != 0 && tilesTemp[y][x].getValue() != tilesTemp[y][z].getValue())
                            {
                                break;
                            }

                            if (tilesTemp[y][x].getValue() == 0 && tilesTemp[y][z].getValue() != 0)
                            {
                                tilesTemp[y][x].setValue(tilesTemp[y][z].getValue());
                                tilesTemp[y][z].setValue(0);
                                moved = true;
                            }

                            if (tilesTemp[y][x].getValue() == tilesTemp[y][z].getValue() && tilesTemp[y][x].getValue() != 0)
                            {
                                tilesTemp[y][x].setValue(tilesTemp[y][x].getValue() + tilesTemp[y][z].getValue());
                                tilesTemp[y][z].setValue(0);
                                setScore(score.getValue() + tilesTemp[y][x].getValue());
                                moved = true;
                                break;
                            }
                        }
                    }
                }

                this.tiles = transpose(tilesTemp);
                break;

            case UP:
                Tile[][] tilesTemp2 = transpose(this.tiles);

                for (int y = 0; y < tilesTemp2.length; y++)
                {
                    for (int x = 0; x < tilesTemp2[y].length - 1; x++)
                    {
                        for (int z = x + 1; z < tilesTemp2[y].length; z++)
                        {
                            if (tilesTemp2[y][x].getValue() != 0 && tilesTemp2[y][z].getValue() != 0 && tilesTemp2[y][x].getValue() != tilesTemp2[y][z].getValue())
                            {
                                break;
                            }

                            if (tilesTemp2[y][x].getValue() == 0 && tilesTemp2[y][z].getValue() != 0)
                            {
                                tilesTemp2[y][x].setValue(tilesTemp2[y][z].getValue());
                                tilesTemp2[y][z].setValue(0);
                                moved = true;
                            }

                            if (tilesTemp2[y][x].getValue() == tilesTemp2[y][z].getValue() && tilesTemp2[y][x].getValue() != 0)
                            {
                                tilesTemp2[y][x].setValue(tilesTemp2[y][x].getValue() + tilesTemp2[y][z].getValue());
                                tilesTemp2[y][z].setValue(0);
                                setScore(score.getValue() + tilesTemp2[y][x].getValue());
                                moved = true;
                                break;
                            }
                        }
                    }
                }

                this.tiles = transpose(tilesTemp2);
                break;
        }

        if (moved)
            createNewTile(tiles);

        isGameOver.setValue(checkForLoss());
    }

    /**
     * Randomly generates a new tile on the board.
     */
    public void createNewTile(Tile[][] tiles)
    {
        List<Tile> tilesList = new ArrayList<>();

        for (Tile[] e : tiles)
        {
            tilesList.addAll(Arrays.asList(e));
        }

        tilesList = tilesList.stream()
                .filter(tile -> tile.getValue() == 0)
                .collect(Collectors.toList());

        Random r = new Random();

        Tile tile = tilesList.get(r.nextInt(tilesList.size()));
        tile.setValue(getNewTileValue());
    }

    /**
     * Gets a random value for a new tile.
     *
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
        for (Tile[] row : tiles)
        {
            // Check if there are any zeros. If there are then the game continues.
            boolean containsZeros = Arrays.stream(row).anyMatch(value -> value.getValue() == 0);
            if (containsZeros)
                return false;
        }

        // if not, check if a value matches any values around it.
        Tile[][] transposed = transpose(tiles);
        for (int i = 0; i < tiles.length; i++)
        {
            if (hasMatchingAdjacentIntegers(tiles[i]) || hasMatchingAdjacentIntegers(transposed[i]))
                return false;
        }

        // Otherwise we cannot move and return true;
        return true;
    }

    /**
     * Returns a transposed form of the Tile 2D array.
     * Rows become columns and vice versa.
     *
     * @param original 2D array to transpose.
     * @return Transposed 2D array.
     */
    private Tile[][] transpose(Tile[][] original)
    {
        Tile[][] transposed = new Tile[original.length][original[0].length];

        for (int i = 0; i < original.length; i++)
        {
            for (int j = 0; j < original[0].length; j++)
            {
                transposed[j][i] = original[i][j];
            }
        }

        return transposed;
    }

    public IntegerProperty getHighScoreProperty()
    {
        return highScore;
    }

    public BooleanProperty isGameOverProperty()
    {
        return isGameOver;
    }

    public boolean hasMatchingAdjacentIntegers(Tile[] row)
    {
        for (int i = 0; i < row.length - 1; i++)
        {
            if (row[i].getValue() == row[i + 1].getValue())
                return true;
        }

        return false;
    }
}
