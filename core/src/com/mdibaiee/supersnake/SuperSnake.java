package com.mdibaiee.supersnake;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mdibaiee.supersnake.Colors;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class SuperSnake extends ApplicationAdapter {
    public int WIDTH = 800;
    public int HEIGHT = 480;

    int points = 0;

	SpriteBatch batch;
    ShapeRenderer shapeRenderer;
	Texture img;
    Snake snake;
    BitmapFont font;

    private Array<Ball> balls = new Array<Ball>();

	@Override
	public void create () {
		batch = new SpriteBatch();
        font = new BitmapFont();

        WIDTH = Gdx.graphics.getBackBufferWidth();
        HEIGHT = Gdx.graphics.getBackBufferHeight();

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

        if (balls.size < 1) {
            balls.add(new Ball((float) Math.random() * WIDTH, (float) Math.random() * HEIGHT));
        }

        snake.draw(shapeRenderer);
        snake.move();

        for(Ball b: balls) {
            b.draw(shapeRenderer);
        }

        if (snake.distance(balls.first()) < balls.first().size) {
            balls.removeIndex(0);
            points += 1;
            snake.addTail();
        }

        batch.begin();
        font.draw(batch, Integer.toString(points), 10, 25);
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
