package com.mdibaiee.supersnake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;

class Tail extends DirectedPoint {
    public float length;
    public boolean breaking;

    public Tail(float x, float y, Direction direction, float length) {
        super(x, y, direction);
        this.length = length;
    }

    public Tail(float x, float y, Direction direction, boolean breaking) {
        super(x, y, direction);
        this.breaking = breaking;
    }
}

public class Snake extends DirectedPoint {
    public Color color = Colors.snake;
    public int size = 10;
    public int point = 0;

    private Array<Tail> tail = new Array<Tail>();

    public float speed = 4;
    public int lives = 3;

    public Snake(float x, float y, int length) {
        super(x, y, Direction.Left);
        tail.insert(0, new Tail(x + length, y, direction, length));
    }

    private void draw_line(ShapeRenderer shapeRenderer, float x0, float y0, float x1, float y1) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rectLine(x0, y0, x1, y1, size);
        shapeRenderer.end();
    }

    public void draw(ShapeRenderer shapeRenderer) {
        draw_line(shapeRenderer, this.x, this.y, tail.first().x, tail.first().y);

        for (int i = 0; i < tail.size - 1; i++) {
            Tail t = tail.get(i);
            if (t.breaking) continue;
            Tail n = tail.get(i + 1);
            draw_line(shapeRenderer, t.x, t.y, n.x, n.y);
        }
    }

    public boolean isDead() {
        return lives < 1;
    }

    @Override
    public void setDirection(Direction direction) {
        if (Direction.oppositeDirections(this.direction, direction)) return;
        if (this.direction == direction) return;

        tail.insert(0, new Tail(x, y, direction, 0));

        this.direction = direction;
    }

    private int cx(Direction direction) {
        if (direction == Direction.Left) return -1;
        if (direction == Direction.Right) return 1;
        return 0;
    }

    private int cy(Direction direction) {
        if (direction == Direction.Up) return 1;
        if (direction == Direction.Down) return -1;
        return 0;
    }

    public void move() {
        float ox = x;
        float oy = y;
        boolean cycled = this.move(x + cx(direction) * speed,
                                   y + cy(direction) * speed);

        // Gdx.app.log("Snake", "(" + ox + ", " + oy + ") > " + "(" + x + ", " + y + ")");

        if (cycled) {
            tail.insert(0, new Tail(ox, oy, direction, 0));
            tail.insert(0, new Tail(x, y, direction, true));
        }

        Tail first = tail.first();
        first.length += speed;

        Tail last = tail.peek();
        last.move(last.x + cx(last.direction) * speed, last.y + cy(last.direction) * speed);
        last.length -= speed;

        while (tail.peek().length <= 0) {
            float diff = tail.peek().length;
            tail.removeIndex(tail.size - 1);
            tail.peek().length += diff;
        }
    }

    public void addTail() {
        Tail last = tail.peek();
        last.length += 25;
        last.x -= cx(last.direction) * 25;
        last.y -= cy(last.direction) * 25;

        speed += 1;
    }

    public void clearTail() {
        tail.removeRange(1, tail.size);
    }

    public boolean head_collision(SizedPoint p) {
        SizedPoint s = new SizedPoint(x - size / 2, y - size / 2, size);
        return s.collides(p);
    }
}
