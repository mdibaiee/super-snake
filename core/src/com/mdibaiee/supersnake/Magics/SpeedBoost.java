package com.mdibaiee.supersnake.Magics;

import com.badlogic.gdx.utils.Array;
import com.mdibaiee.supersnake.Magic;
import com.mdibaiee.supersnake.Snake;

public class SpeedBoost extends Magic {
    private Snake snake;
    private int seconds = 30;

    private float original_x;
    private float original_y;

    public SpeedBoost(float x, float y) {
        super(x, y, "green.png");
        original_x = x;
        original_y = y;
    }

    public void action(Snake snake, Array<Magic> magics) {
        this.snake = snake;
        snake.speed += 5;
    }

    public boolean draw() {
        double rx = Math.random() * 5;
        double ry = Math.random() * 5;

        x = (float) (original_x + rx);
        y = (float) (original_y + ry);

        return super.draw();
    }

    public boolean iter() {
        frames++;

        if (frames > seconds * 60) {
            snake.speed -= 5;
            return true;
        }

        return false;
    }
}
