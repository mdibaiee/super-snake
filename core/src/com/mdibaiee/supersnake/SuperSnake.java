package com.mdibaiee.supersnake;
import com.mdibaiee.supersnake.Magics.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class SuperSnake extends ApplicationAdapter {
    public int WIDTH = 800;
    public int HEIGHT = 480;

	SpriteBatch batch;
    ShapeRenderer shapeRenderer;
	Texture img;
    Snake snake;
    BitmapFont font;

    private Array<Magic> magics = new Array<Magic>();

	@Override
	public void create () {
		batch = new SpriteBatch();
        font = new BitmapFont();

        WIDTH = Gdx.graphics.getBackBufferWidth();
        HEIGHT = Gdx.graphics.getBackBufferHeight();

        Gdx.app.log("Snake", "VIEWPORT " + WIDTH + ", " + HEIGHT);

        shapeRenderer = new ShapeRenderer();

        snake = new Snake(WIDTH / 2, HEIGHT / 2, 50);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(Colors.background.r, Colors.background.g, Colors.background.b, Colors.background.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();

            if (snake.direction == Direction.Left || snake.direction == Direction.Right) {
                if (y < HEIGHT / 2) {
                    snake.setDirection(Direction.Up);
                } else {
                    snake.setDirection(Direction.Down);
                }
            } else {
                if (x < WIDTH / 2) {
                    snake.setDirection(Direction.Left);
                } else {
                    snake.setDirection(Direction.Right);
                }
            }
        }

        int drawn_magics = 0;

        for (Magic m: magics) {
            if (!m.active) drawn_magics++;
        }

        if (drawn_magics < 3) {
            double r = Math.random() * 100;
            float mx = (float) Math.random() * WIDTH;
            float my = (float) Math.random() * HEIGHT;

            Magic newMagic;
            if (r < 70) {
                newMagic = new StarPoint(mx, my);
            } else if (r < 80) {
                newMagic = new SpeedBoost(mx, my);
            } else if (r < 90) {
                newMagic = new Growth(mx, my);
            } else {
                newMagic = new Skull(mx, my);
            }

            newMagic = new SpeedBoost(mx, my);

            magics.add(newMagic);
        }

        snake.draw(shapeRenderer);
        snake.move();

        for(Magic m: magics) {
            if (m.active) {
                if (m.iter()) {
                    magics.removeValue(m, true);
                }
            } else {
                if (m.draw()) {
                    magics.removeValue(m, true);
                } else if (snake.head_collision(m)) {
                    m.action(snake);
                    m.active = true;
                }
            }
        }

        batch.begin();
        font.draw(batch, Integer.toString(snake.point), 10, 25);
        batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
        font.dispose();
        shapeRenderer.dispose();
	}
}
