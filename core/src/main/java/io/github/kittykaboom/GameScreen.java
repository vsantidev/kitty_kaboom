package io.github.kittykaboom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.kittykaboom.Objects.Obstacle;
import io.github.kittykaboom.Player.Player;


public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Player player;
    private Array<Obstacle> obstacles;

    public GameScreen() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        batch = new SpriteBatch();
        player = new Player(0, 0);
        obstacles = new Array<>();

        obstacles.add(new Obstacle("textures/wall.png", 50, 50));
        obstacles.add(new Obstacle("textures/wall.png", 100, 100));
    }

    @Override
    public void show() {}
    @Override
    public void render(float delta) {
        update(delta);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.render(batch);
        for (Obstacle obstacle : obstacles) {
            obstacle.render(batch);
        }
        batch.end();
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}

    private void update(float delta) {
        float speed = 100 * delta;
        Vector2 movement = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) movement.y += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) movement.y -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) movement.x -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) movement.x += speed;

        // Essai de déplacement et gestion des collisions
        player.move(movement.x, movement.y);
        for (Obstacle obstacle : obstacles) {
            if (player.getBounds().overlaps(obstacle.getBounds())) {
                // Réinitialiser la position du joueur si collision
                player.move(-movement.x, -movement.y);
                break;
            }
        }
    }
}
