package com.mdibaiee.supersnake.Magics;

import com.badlogic.gdx.utils.Array;
import com.mdibaiee.supersnake.Magic;
import com.mdibaiee.supersnake.Snake;


public class Flip extends Magic {
    private Snake snake;

    public Flip(float x, float y) {
        super(x, y, "spiral.png");
    }

    public void action(Snake snake, Array<Magic> magics) {
        for (int i = 0; i < magics.size; i++) {
            Magic m = magics.get(i);
            m.move(m.y, m.x);
        }

        this.snake = snake;
    }

    public boolean draw() {
        rotation = drawn % 360;

        return super.draw();
    }

    public boolean iter() {
        return true;
    }
}
