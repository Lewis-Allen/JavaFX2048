package com.lewisallen.javafx2048;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class Tile extends StackPane
{
    private Rectangle background;
    private IntegerProperty value;

    private int MAX_VALUE = 2048;
    private int MIN_VALUE = 2;

    private Color lightest = Color.WHITE;
    private Color darkest = Color.BLACK;

    public Tile(int width, int height, int v)
    {
        background = new Rectangle(width, height);
        value = new SimpleIntegerProperty(v);

        background.fillProperty().bind(Bindings.createObjectBinding(() ->
        {
            double ratio = (((double) value.get() - MIN_VALUE) / (MAX_VALUE - MIN_VALUE));

            double red = ((darkest.getRed() - lightest.getRed()) * ratio + lightest.getRed());
            double green = ((darkest.getGreen() - lightest.getGreen()) * ratio + lightest.getGreen());
            double blue = ((darkest.getBlue() - lightest.getBlue()) * ratio + lightest.getBlue());

            return Color.color(Math.min(red, 1), Math.min(green, 1), Math.min(blue, 1));
        }, value));

        Label label = new Label();
        label.textProperty().bind(value.asString());
        label.setFont(new Font("Arial", 30));
        getChildren().addAll(background, label);
    }

    public Tile(int width, int height)
    {
        this(width, height, 0);
    }


    public int getValue()
    {
        return value.get();
    }

    public void setValue(int value)
    {
        this.value.setValue(value);
    }

    public IntegerProperty valueProperty()
    {
        return value;
    }
}
