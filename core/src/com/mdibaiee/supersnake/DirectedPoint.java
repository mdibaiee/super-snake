package com.mdibaiee.supersnake;


public class DirectedPoint extends Point {
    public Direction direction;

    public DirectedPoint(float x, float y, Direction direction) {
        super(x, y);
        this.direction = direction;
    }

    public void setDirection(Direction direction) {
        if (Direction.oppositeDirections(this.direction, direction)) return;
        this.direction = direction;
    }
}
