package com.mdibaiee.supersnake;

public class SizedPoint extends Point {
    public float size;

    public SizedPoint(float x, float y, float size) {
        super(x, y);

        this.size = size;
    }

    public float centerX() {
        return x + size / 2;
    }
    public float centerY() {
        return y + size / 2;
    }

    public double distance(SizedPoint other) {
        return Math.sqrt(Math.pow(centerX() - other.centerX(), 2)
                       + Math.pow(centerY() - other.centerY(), 2));
    }

    public boolean collides(SizedPoint other) {
        return distance(other) < (size / 2 + other.size / 2);
    }
}
