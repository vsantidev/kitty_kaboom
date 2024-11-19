package io.github.kittykaboom;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.kittykaboom.Items.Special.YarnBallPower;
import io.github.kittykaboom.Items.Special.YarnBallUp;
import io.github.kittykaboom.Items.YarnBall.Explosion;
import io.github.kittykaboom.Items.YarnBall.YarnBall;
import io.github.kittykaboom.Players.CatPlayer;
import io.github.kittykaboom.Players.Player;
import io.github.kittykaboom.Walls.Wall;

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Player player;
    private ShapeRenderer shapeRenderer;
    private Main main;
    private boolean gameOver = false;
    private List<Wall> walls;
    private List<Explosion> explosions = new ArrayList<>();
    private List<Rectangle> explosionAreas;

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
        shapeRenderer = new ShapeRenderer();
    }


    // _________________ GETTERS & SETTERS _________________

    public GameMap getGameMap() {
        return gameMap;
    }

    // _________________ METHODS _________________

    public void addExplosion(float x, float y) {
        explosions.add(new Explosion(x, y));
    }

    private void renderExplosions(float delta, SpriteBatch batch) {
        List<Explosion> explosionsToRemove = new ArrayList<>();
        for (Explosion explosion : explosions) {
            if (explosion.update(delta)) {
                explosionsToRemove.add(explosion); // Explosion terminée
            }
            explosion.render(batch);
            Rectangle explosionArea = explosion.getBounds(); // Assuming Explosion has a getBounds() method
            //handleExplosion(explosionArea);
        }
        explosions.removeAll(explosionsToRemove);
    }

    public void handleExplosionImpact(List<Rectangle> affectedAreas) {
        this.explosionAreas = affectedAreas;
    }

    public boolean isSolidWall(int cellX, int cellY) {
        return gameMap.isSolidWall(cellX, cellY);
    }

    // Méthode pour vérifier si le jeu est terminé
    public boolean isGameOver() {
        return gameOver;
    }

    public void endGame() {
        gameOver = true;
        System.out.println("Game Over! Ending the game.");
    }


    public void restartGame() {
        System.out.println("Restarting game...");
        main.setScreen(new GameScreen()); // Réinitialise l'écran
    }
    


    // ========== Show ==========
    @Override
    public void show() {}

    // ========== Render ==========
    @Override
    public void render(float delta) {
        update(delta); // Appel de la méthode de mise à jour (si nécessaire)

        Gdx.gl.glClearColor(0, 0, 0, 1); // Définit une couleur de fond noire
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Efface l'écran

        //camera.position.set(400, 300, 0);
        camera.position.set((GameMap.getTotalCols() * GameMap.getCellWidth()) / 2f, (GameMap.getTotalRows() * GameMap.getCellHeight()) / 2f, 0);
        camera.update(); // Met à jour la caméra
        batch.setProjectionMatrix(camera.combined); // Définit la matrice de projection

    if (gameOver) {
        // Efface l'écran
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Affiche le message Game Over
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2);
        font.draw(batch, "Game Over!", 300, 400);
        font.draw(batch, "Press R to Restart", 300, 250);
        font.draw(batch, "Press Q to Quit", 300, 200);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            restartGame();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)){
            Gdx.app.exit();
        }
        return; // Arrête le rendu normal
    }

        batch.begin();

        // Dessin des murs
        for (Wall wall : walls) {
            wall.render(batch);
        }

        // Dessin du joueur et des balles de laine posées
        if (player != null) {
            player.render(batch);
        }

        // Dessin des YarnBallUp
        for (YarnBallUp yarnBallUp : gameMap.getYarnBallUps()) {
            yarnBallUp.render(batch);
        }

        // Dessin des YarnBallPower
        for (YarnBallPower yarnBallPower : gameMap.getYarnBallPowers()) {
            yarnBallPower.render(batch);
        }

        renderExplosions(delta, batch);

        batch.end();
    }
    

    // ========== Resize ==========
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    // ========== Pause ==========
    @Override
    public void pause() {}

    // ========== Resume ==========
    @Override
    public void resume() {}

    // ========== Hide ==========
    @Override
    public void hide() {}

    // ========== Dispose ==========
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

        shapeRenderer.dispose();

    }


    // ================ Update ===========================
    private void update(float delta) {
        
        if (player == null) return;

        // Vector2 movement = new Vector2();
        float speed = 100 * delta; // Vitesse du joueur en fonction du temps écoulé
        float dx = 0;
        float dy = 0;

        // Vérifiez les touches pressées et mettez à jour dx, dy en conséquence
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) dy += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) dy -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) dx -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dx += speed;


        // Vérifiez si la touche SPACE est pressée pour poser une balle de laine
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (player instanceof CatPlayer) {
                ((CatPlayer) player).placeYarnBall(this);
            }
        }

        // Debug : Affiche les valeurs de dx et dy
        // System.out.println("dx: " + dx + ", dy: " + dy);

        // Enregistrer la position actuelle du joueur pour restaurer en cas de collision
        float originalX = player.getBounds().x;
        float originalY = player.getBounds().y;

        // Déplace le joueur temporairement
        player.move(dx, dy);
        

        // Vérification de collision avec chaque mur
        boolean collided = false;
        for (Wall wall : walls) {
            if (player.getBounds().overlaps(wall.getBounds())) {
                collided = true;
                break;
            }
        }

        // Vérifie la collision entre le joueur et chaque boule de laine
        boolean collidedWithYarnBall = false;
        for (YarnBall yarnBall : ((CatPlayer) player).getYarnBalls()) {
            if (yarnBall.isBlocking() && player.getBounds().overlaps(yarnBall.getBounds())) {
                collidedWithYarnBall = true;
                break;
            }
        }
        // Si une collision avec une boule de laine, annulez le déplacement
        if (collidedWithYarnBall) {
            player.move(-dx, -dy);
        }

        // Si une collision est détectée, rétablissez la position initiale
        if (collided) {
            player.move(-dx, -dy);
        }
        // Vérifiez les collisions avec YarnBallUp
        List<YarnBallUp> itemsToRemoveUps = new ArrayList<>();
        for (YarnBallUp yarnBallUp : gameMap.getYarnBallUps()) {
            if (player.getBounds().overlaps(yarnBallUp.getBounds())) {
                ((CatPlayer) player).increaseMaxYarnBalls(); // Augmenter la capacité du joueur
                itemsToRemoveUps.add(yarnBallUp); // Ajoute l'item à supprimer
                System.out.println("Collected YarnBallUp! Max YarnBalls: " + ((CatPlayer) player).getMaxYarnBalls());
            }
        }

        // Vérifiez les collisions avec YarnBallPower
        List<YarnBallPower> itemsToRemovePowers = new ArrayList<>();
        for (YarnBallPower yarnBallPower : gameMap.getYarnBallPowers()) {
            if (player.getBounds().overlaps(yarnBallPower.getBounds())) {
                ((CatPlayer) player).increaseMaxYarnBallsPower(); // Augmente la puissance du joueur
                itemsToRemovePowers.add(yarnBallPower); // Ajoute l'item à supprimer
                System.out.println("Collected YarnBall POWER! Max YarnPOWER: " + ((CatPlayer) player).getMaxYarnBallsPower());
            }
        }

        // Retirez les items ramassés
        gameMap.getYarnBallUps().removeAll(itemsToRemoveUps);
        gameMap.getYarnBallPowers().removeAll(itemsToRemovePowers);
        // Met à jour les balles de laine et leur explosion
        ((CatPlayer) player).update(delta);

    }
    
}