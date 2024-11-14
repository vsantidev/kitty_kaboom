package io.github.kittykaboom;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.kittykaboom.Players.Player;
import io.github.kittykaboom.Walls.Wall;

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Player player;
    private List<Wall> walls;

    private GameMap gameMap;
    
    // _________________ CONSTRUCTOR _________________
    public GameScreen() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        batch = new SpriteBatch();

        // Initialise la carte
        gameMap = new GameMap("map.txt");
        walls = gameMap.getWalls();
        player = gameMap.getPlayer();
    }

    // _________________ METHODS _________________

    @Override
    public void show() {}
    @Override
    public void render(float delta) {
        update(delta); // Appel de la méthode de mise à jour (si nécessaire)

        Gdx.gl.glClearColor(0, 0, 0, 1); // Définit une couleur de fond noire
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Efface l'écran

        //camera.position.set(400, 300, 0);
        camera.position.set((GameMap.getTotalCols() * GameMap.getCellWidth()) / 2f, (GameMap.getTotalRows() * GameMap.getCellHeight()) / 2f, 0);
        camera.update(); // Met à jour la caméra
        batch.setProjectionMatrix(camera.combined); // Définit la matrice de projection

        batch.begin();

        // Boucle pour rendre chaque mur et ainsi que celui du joueur
        for (Wall wall : walls) {
            wall.render(batch);
        }

        if (player != null) {
            player.render(batch);
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
    public void dispose() {
        batch.dispose();

        // Dispose de toutes les textures des murs
        for (Wall wall : walls) {
            if (wall.getTexture() != null) {
                wall.getTexture().dispose();
            }
        }

        // Dispose de la texture du joueur s'il est initialisé
        if (player != null && player.getTexture() != null) {
            player.getTexture().dispose();
        }
    }

    private void update(float delta) {
        
        if (player == null) return;
        float speed = 100 * delta;
        Vector2 movement = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) movement.y += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) movement.y -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) movement.x -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) movement.x += speed;

        // Essai de déplacement et gestion des collisions
        player.move(movement.x, movement.y);
        for (Wall wall : walls) {
            if (player.getBounds().overlaps(wall.getBounds())) {
                // Réinitialiser la position du joueur si collision
                player.move(-movement.x, -movement.y);
                break;
            }
        }
    }
}
