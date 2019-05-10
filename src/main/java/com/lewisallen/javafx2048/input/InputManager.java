package com.lewisallen.javafx2048.input;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public class InputManager
{
    private Map<KeyCode, Boolean> keyPressed = new HashMap<>();
    private Map<KeyCode, Boolean> keyHeld = new HashMap<>();

    /**
     * Sets a key as pressed.
     * Sets key as held if key has not been released since last pressed.
     * @param keyCode KeyCode of the key pressed.
     */
    public void keyPressed(KeyCode keyCode)
    {
        if (!keyPressed.containsKey(keyCode))
        {
            keyPressed.put(keyCode, Boolean.TRUE);
            keyHeld.put(keyCode, Boolean.FALSE);
        }
        else
        {
            if (keyHeld.get(keyCode))
                return;

            if (keyPressed.get(keyCode))
                keyHeld.put(keyCode, Boolean.TRUE);

            keyPressed.put(keyCode, Boolean.TRUE);
        }
    }

    /**
     * Sets a key as not pressed.
     * @param keyCode Key code of the key pressed.
     */
    public void keyReleased(KeyCode keyCode)
    {
        keyPressed.put(keyCode, Boolean.FALSE);
        keyHeld.put(keyCode, Boolean.FALSE);
    }

    /**
     * Getter for whether a key is pressed.
     * Takes into account when a key is being held.
     * Held keys return as not pressed.
     * @param keyCode Key code of the key to check.
     * @return Whether the key is pressed.
     */
    public boolean isPressed(KeyCode keyCode)
    {
        if (!keyPressed.containsKey(keyCode))
        {
            keyPressed.put(keyCode, Boolean.FALSE);
            keyHeld.put(keyCode, Boolean.FALSE);
        }

        // Only return true if the key is pressed and isn't being held.
        return keyPressed.get(keyCode) && !keyHeld.get(keyCode);
    }
}
