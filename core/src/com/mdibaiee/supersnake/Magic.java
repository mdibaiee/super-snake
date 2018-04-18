package com.mdibaiee.supersnake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public abstract class Magic extends SizedPoint {
    SpriteBatch batch;
    private Texture image;
    public boolean active = false;
    public int frames = 0;
    public int drawn = 0;
    public int lifetime = 20 * 60;

    public int srcX = 0;
    public int srcY = 0;

    public int srcWidth = 0;
    public int srcHeight = 0;

    public float rotation = 0;
    public float scale = 1;

    private float drawn_width;
    private float drawn_height;

    public Magic(float x, float y, String image_url) {
        super(x, y, 40);

        image = new Texture(Gdx.files.internal(image_url));
        batch = new SpriteBatch();

        srcWidth = image.getWidth();
        srcHeight = image.getHeight();
        float ratio = (float) srcHeight / (float) srcWidth;

        drawn_width = size;
        drawn_height = size * ratio;
        size = Math.max(size, size * ratio);
    }

    public boolean draw() {
        drawn += 1;
        float width = image.getWidth();
        float height = image.getHeight();

        batch.begin();

        batch.draw(image, x, y,
                   size / 2,  size / 2,
                   drawn_width, drawn_height,
                   scale, scale,
                   rotation,
                   srcX, srcY,
                   srcWidth, srcHeight,
                   false, false);

        batch.end();

        lifetime -= 1;

        if (lifetime <= 0) {
            return true;
        }

        return false;
    }

    abstract public void action(Snake snake, Array<Magic> magics);

    // this method will be called after action at each iteration until it returns true
    // when it does return true ,the object is disposed
    abstract public boolean iter();
}
