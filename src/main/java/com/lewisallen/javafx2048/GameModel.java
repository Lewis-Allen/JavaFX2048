package com.lewisallen.javafx2048;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameModel
{
    private IntegerProperty score = new SimpleIntegerProperty(0),
            highScore = new SimpleIntegerProperty(0);

    private Tile[][] rows = new Tile[4][4],
            columns = new Tile[4][4];

    private List<Tile> tilesList = new ArrayList<>();

    private BooleanProperty isGameOver = new SimpleBooleanProperty(false);

    private Random random = new Random();

    private boolean moved;

    public GameModel()
    {
        // Store Tiles in three different forms - Rows, Columns and as a standard list.
        for (int y = 0; y < 4; y++)
        {
            for (int x = 0; x < 4; x++)
            {
                rows[y][x] = new Tile(100, 100);
                columns[x][y] = rows[y][x];
                tilesList.add(rows[y][x]);
            }
        }

        createNewTile();
        createNewTile();
    }

    public Tile getTile(int x, int y)
    {
        return rows[y][x];
    }

    /**
     * Update score and highscore.
     *
     * @param score new score.
     */
    public void setScore(int score)
    {
        this.score.setValue(score);
        if (score > highScore.get())
        {
            highScore.setValue(score);
        }
    }

    /**
     * Getter for scoreProperty
     *
     * @return scoreProperty
     */
    public IntegerProperty scoreProperty()
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
        tilesList.forEach((tile) -> tile.setValue(0));
        createNewTile();
        createNewTile();
    }

    /**
     * Move the tiles on the game board based on user input.
     *
     * @param move Direction to move the tiles in.
     */
    public void move(Move move)
    {
        System.out.println("Moving " + move);

        moved = false; // Did anything actually move on input.
        boolean shouldBreak;

        switch (move)
        {
            case LEFT:
                for (Tile[] row : rows)
                {
                    for (int x1 = 0; x1 < row.length - 1; x1++)
                    {
                        for (int x2 = x1 + 1; x2 < row.length; x2++)
                        {
                            shouldBreak = moveHelper(row, x1, x2);

                            if (shouldBreak)
                                break;
                        }
                    }
                }
                break;

            case RIGHT:
                for (Tile[] row : rows)
                {
                    for (int x1 = rows.length - 1; x1 > 0; x1--)
                    {
                        for (int x2 = x1 - 1; x2 >= 0; x2--)
                        {
                            shouldBreak = moveHelper(row, x1, x2);

                            if (shouldBreak)
                                break;
                        }
                    }
                }
                break;

            case DOWN:
                for (Tile[] column : columns)
                {
                    for (int x1 = columns.length - 1; x1 > 0; x1--)
                    {
                        for (int x2 = x1 - 1; x2 >= 0; x2--)
                        {
                            shouldBreak = moveHelper(column, x1, x2);

                            if (shouldBreak)
                                break;
                        }
                    }
                }
                break;

            case UP:
                for (Tile[] column : columns)
                {
                    for (int x1 = 0; x1 < column.length - 1; x1++)
                    {
                        for (int x2 = x1 + 1; x2 < column.length; x2++)
                        {
                            shouldBreak = moveHelper(column, x1, x2);

                            if (shouldBreak)
                                break;
                        }
                    }
                }
                break;
        }

        if (moved)
            createNewTile();

        isGameOver.setValue(checkForLoss());
    }

    /**
     * Given a row/column, works out if tile x2 should merge with x1.
     *
     * @param row Row of tiles on the board.
     * @param x1  position of first tile in row.
     * @param x2  position of second tile in row.
     * @return Whether loop should be broken out of when exiting this method.
     */
    private Boolean moveHelper(Tile[] row, int x1, int x2)
    {
        /*
        Check if the tiles have different non-zero values. If so, return true and cause a break
        as no merging can occur.
        */
        if (row[x1].getValue() != 0 && row[x2].getValue() != 0 && row[x1].getValue() != row[x2].getValue())
        {
            return true;
        }

        // Check if x1 is empty and therefore x2 can move into x1's position.
        if (row[x1].getValue() == 0 && row[x2].getValue() != 0)
        {
            row[x1].setValue(row[x2].getValue());
            row[x2].setValue(0);
            moved = true;
        }

        /*
        Check if values are the same and therefore a merge can occur.
        If a merge occurs, return true and cause a break so subsequent merges
        do not occur for this tile during this input.
        */
        if (row[x1].getValue() == row[x2].getValue() && row[x1].getValue() != 0)
        {
            row[x1].setValue(row[x1].getValue() + row[x2].getValue());
            row[x2].setValue(0);
            setScore(score.getValue() + row[x1].getValue());
            moved = true;
            return true;
        }

        return false;
    }

    /**
     * Randomly generates a new tile on the board.
     */
    private void createNewTile()
    {
        List<Tile> empties = tilesList.stream()
                .filter(tile -> tile.getValue() == 0)
                .collect(Collectors.toList());

        Tile tile = empties.get(random.nextInt(empties.size()));
        tile.setValue(getNewTileValue());
    }

    /**
     * Gets a random value for a new tile.
     *
     * @return value for tile.
     */
    private int getNewTileValue()
    {
        return random.nextInt(100) < 10 ? 4 : 2;
    }

    /**
     * Check whether the game is over.
     */
    private boolean checkForLoss()
    {
        // Check if there are any zeros,
        boolean containsEmpties = tilesList.stream().anyMatch(val -> val.getValue() == 0);
        if (containsEmpties)
            return false;

        // if not, check if a value matches any values around it.
        for (int i = 0; i < rows.length; i++)
        {
            if (hasMatchingAdjacentIntegers(rows[i]) || hasMatchingAdjacentIntegers(columns[i]))
                return false;
        }

        // Otherwise we cannot move and return true;
        return true;
    }

    /**
     * Getter for highScopeProperty
     *
     * @return highScoreProperty
     */
    public IntegerProperty highScoreProperty()
    {
        return highScore;
    }

    /**
     * Getter for gameOverProperty
     *
     * @return gameOverProperty
     */
    public BooleanProperty gameOverProperty()
    {
        return isGameOver;
    }

    /**
     * Works out if any adjacent tiles in a row have an identical value.
     *
     * @param row Row of tiles to examine.
     * @return Whether any of the tiles have an matching adjacent tile.
     */
    private boolean hasMatchingAdjacentIntegers(Tile[] row)
    {
        for (int i = 0; i < row.length - 1; i++)
        {
            if (row[i].getValue() == row[i + 1].getValue())
                return true;
        }

        return false;
    }
}
