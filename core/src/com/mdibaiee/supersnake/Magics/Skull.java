package com.mdibaiee.supersnake.Magics;

import com.badlogic.gdx.utils.Array;
import com.mdibaiee.supersnake.Colors;
import com.mdibaiee.supersnake.Magic;
import com.mdibaiee.supersnake.Snake;

public class Skull extends Magic {
    private Snake snake;
    private int seconds = 7;
    private int original_points;

    public Skull(float x, float y) {
        super(x, y, "skull.png");
    }

    public boolean draw() {
        float rx = (float) Math.random() * 30 - 15;
        rotation = rx;

        return super.draw();
    }

    public void action(Snake snake, Array<Magic> magics) {
        this.snake = snake;
        original_points = snake.point;
        snake.color = Colors.red;
    }

    public boolean iter() {
        frames++;

        if (frames > seconds * 60) {
            snake.color = Colors.snake;
            if (snake.point <= original_points) { snake.lives -= 1; }
            return true;
        }

        return false;
    }
}
