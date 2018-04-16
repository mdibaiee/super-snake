package com.mdibaiee.supersnake;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;

public class Ball extends Point {
    private Color color = Colors.ball;

    public int radius = 15;
    public int size = radius * 2;

    public Ball(float x, float y) {
        super(x, y);
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.circle(x, y, radius);
        shapeRenderer.end();
    }
}
