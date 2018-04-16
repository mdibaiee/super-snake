package com.mdibaiee.supersnake;


public enum Direction {
    Left, Right,
    Up, Down;

    static public boolean oppositeDirections(Direction a, Direction b) {
        if ((a == Left && b == Right) || (a == Right && b == Left)) return true;
        if ((a == Up && b == Down) || (a == Down && b == Up)) return true;

        return false;
    };
};

