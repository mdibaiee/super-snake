package com.mdibaiee.supersnake;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
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

    private int PADDED_WIDTH;
    private int PADDED_HEIGHT;
    private int PADDING = 20;

    private int INITIAL_SNAKE_LENGTH = 70;

    private boolean paused;

	SpriteBatch batch;
    ShapeRenderer shapeRenderer;
	Texture img;
    Snake snake;
    BitmapFont font;
    BitmapFont fontBig;
    BitmapFont fontBtn;

    Snake pause_snake;
    private int pause_frames = 0;

    private int gameover_timer = 3 * 60;

    private Array<Magic> magics = new Array<Magic>();

	@Override
	public void create () {
		batch = new SpriteBatch();

        WIDTH = Gdx.graphics.getBackBufferWidth();
        HEIGHT = Gdx.graphics.getBackBufferHeight();

        PADDED_WIDTH = WIDTH - PADDING;
        PADDED_HEIGHT = HEIGHT - PADDING;

        Gdx.app.log("Snake", "VIEWPORT " + WIDTH + ", " + HEIGHT);

        shapeRenderer = new ShapeRenderer();

        snake = new Snake(WIDTH / 2, HEIGHT / 2, INITIAL_SNAKE_LENGTH);
        pause_snake = new Snake(WIDTH / 2, 200, 100);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PatrickHand.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 32;
        font = generator.generateFont(parameter);

        parameter.size = 52;
        parameter.borderColor = Colors.spray;
        parameter.borderWidth = 2;
        fontBtn = generator.generateFont(parameter);

        parameter.size = 80;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Colors.red;
        fontBig = generator.generateFont(parameter);

        generator.dispose();
	}

	private Point random_point() {
        float rx = (float) Math.random() * PADDED_WIDTH + PADDING;
        float ry = (float) Math.random() * PADDED_HEIGHT + PADDING;

        return new Point(rx, ry);
    }

    private Direction random_direction() {
        double r = Math.random() * 100;
        if (r < 25) {
            return Direction.Down;
        } else if (r < 50) {
            return Direction.Up;
        } else if (r < 75) {
            return Direction.Left;
        } else {
            return Direction.Right;
        }
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(Colors.background.r, Colors.background.g, Colors.background.b, Colors.background.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if(Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();

            if (x > WIDTH - 150 && y > HEIGHT - 60) {
                paused = true;
                return;
            } else if (paused) {
                paused = false;
                return;
            }

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

        if (paused) {
            batch.begin();
            fontBtn.draw(batch, "Super Snake", WIDTH / 2 - 125, HEIGHT - 150);

            font.draw(batch, "Simple, fast-paced snake game packed with extra fun!", WIDTH / 2 - 300, HEIGHT - 250);
            font.draw(batch, "by Mahdi Dibaiee", WIDTH / 2 - 100, HEIGHT - 300);

            font.draw(batch, "Source code available on", WIDTH / 2 - 135, HEIGHT - 400);
            font.draw(batch, "github.com/mdibaiee/super-snake", WIDTH / 2 - 200, HEIGHT - 450);

            font.draw(batch, "Powered by LibGDX", WIDTH / 2 - 120, 150);
            batch.end();

            pause_snake.draw(shapeRenderer);
            pause_snake.move();

            int luck = (int) (Math.random() * 30 - 15);
            if (pause_frames % (20 + luck) == 0) {
                pause_snake.setDirection(random_direction());
            }

            if (pause_frames % (250 + luck) == 0 && pause_frames < 1000) {
                pause_snake.addTail();
            }

            pause_frames++;

            return;
        }

        if (snake.isDead()) {
            batch.begin();
            fontBig.draw(batch, "Game Over", WIDTH / 2 - 160, HEIGHT / 2 + 40);
            String seconds = String.format("%.1f", gameover_timer / 60f);
            font.draw(batch, seconds, WIDTH / 2 - 10, HEIGHT / 2 - 50);
            gameover_timer -= 1;
            batch.end();

            if (gameover_timer <= 0) {
                Point r = random_point();
                snake = new Snake(r.x, r.y, INITIAL_SNAKE_LENGTH);
                magics.clear();
                gameover_timer = 3 * 60;
            }

            return;
        }

        int drawn_magics = 0;

        for (Magic m: magics) {
            if (!m.active) drawn_magics++;
        }

        if (drawn_magics < 3) {

            double r = Math.random() * 100;

            Point p = random_point();

            Magic newMagic;
            if (r < 60) {
                newMagic = new StarPoint(p.x, p.y);
            } else if (r < 70) {
                newMagic = new SpeedBoost(p.x, p.y);
            } else if (r < 80) {
                newMagic = new Growth(p.x, p.y);
            } else if (r < 90) {
                newMagic = new Skull(p.x, p.y);
            } else {
                newMagic = new Flip(p.x, p.y);
            }

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
                    m.action(snake, magics);
                    m.active = true;
                }
            }
        }

        batch.begin();
        font.draw(batch, "Points: " + snake.point, 20, 35);
        font.draw(batch, "Lives: " + snake.lives, 150, 35);

        font.draw(batch, "About", WIDTH - 90, 35);
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
