package com.mdibaiee.supersnake.Magics;

import com.badlogic.gdx.utils.Array;
import com.mdibaiee.supersnake.Magic;
import com.mdibaiee.supersnake.Snake;

public class Growth extends Magic {
    private Snake snake;
    private int seconds = 30;

    public Growth(float x, float y) {
        super(x, y, "blue.png");
    }

    public void action(Snake snake, Array<Magic> magics) {
        this.snake = snake;
        snake.size += 10;
    }

    public boolean draw() {
        double cycle = Math.sin(((drawn * 6) % 360) * Math.PI / 360);
        scale = (float) (1 + cycle * 0.3);

        return super.draw();
    }

    public boolean iter() {
        frames++;

        if (frames > seconds * 60) {
            snake.size -= 10;
            return true;
        }

        return false;
    }
}
