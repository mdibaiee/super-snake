package com.mdibaiee.supersnake;

import com.badlogic.gdx.Gdx;

public class Point {
    public float x;
    public float y;

    private int WIDTH = Gdx.graphics.getBackBufferWidth();
    private int HEIGHT = Gdx.graphics.getBackBufferHeight();

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean move(float x, float y) {
        this.x = x;
        this.y = y;

        if (this.x > WIDTH) {
            this.x -= WIDTH;
            return true;
        }
        if (this.y > HEIGHT) {
            this.y -= HEIGHT;
            return true;
        }
        if (this.x < 0) {
            this.x += WIDTH;
            return true;
        }
        if (this.y < 0) {
            this.y += HEIGHT;
            return true;
        }

        return false;
    }

    public double distance(Point p) {
        return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
    }
}
