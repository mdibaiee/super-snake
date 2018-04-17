package com.mdibaiee.supersnake.Magics;

import com.mdibaiee.supersnake.Colors;
import com.mdibaiee.supersnake.Magic;
import com.mdibaiee.supersnake.Snake;

public class Skull extends Magic {
    private Snake snake;
    private int seconds = 15;

    public Skull(float x, float y) {
        super(x, y, "skull.png");
    }

    public void action(Snake snake) {
        this.snake = snake;
        snake.color = Colors.red;
    }

    public boolean iter() {
        frames++;

        if (frames > seconds * 60) {
            snake.color = Colors.snake;
            return true;
        }

        return false;
    }
}
