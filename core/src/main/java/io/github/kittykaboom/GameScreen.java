package io.github.kittykaboom;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.kittykaboom.Items.Special.Mouse;
import io.github.kittykaboom.Items.Special.YarnBallPower;
import io.github.kittykaboom.Items.Special.YarnBallUp;
import io.github.kittykaboom.Items.YarnBall.Explosion;
import io.github.kittykaboom.Items.YarnBall.YarnBall;
import io.github.kittykaboom.Players.CatPlayer;
import io.github.kittykaboom.Walls.Wall;

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private ShapeRenderer shapeRenderer;
    private Main main;
    private Texture gameOverBackground;

    private CatPlayer loser;
    private CatPlayer winner;
    private float gameOverDelay = 2.0f; // 2 secondes de délai avant Game Over
    private boolean transitioningToGameOver = false; // Indique si on est en transition vers Game Over
    
    private boolean gameOver = false;
    private List<Wall> walls;
    private List<Mouse> mice;
    private List<Explosion> explosions = new ArrayList<>();
    private List<Rectangle> explosionAreas;
    private List<CatPlayer> players;

    private GameMap gameMap;
    
    // _________________ CONSTRUCTOR _________________
    public GameScreen() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        batch = new SpriteBatch();
        gameOverBackground = new Texture("textures/game_over_background.png"); // Texture personnalisée
        // Initialise la carte
        gameMap = new GameMap("map.txt");
        walls = gameMap.getWalls();

        //Initialisation des joueurs
        shapeRenderer = new ShapeRenderer();
        players = gameMap.getPlayers();
        // players.add(playerOne);
        // players.add(playerTwo);
    }


    // _________________ GETTERS & SETTERS _________________

    public GameMap getGameMap() {
        return gameMap;
    }

    public List<CatPlayer> getPlayers() {
        return players;
    }
    

    // _________________ METHODS _________________

    public void addExplosion(float x, float y) {
        explosions.add(new Explosion(x, y));
    }

    private void renderExplosions(float delta, SpriteBatch batch) {
        List<Explosion> explosionsToRemove = new ArrayList<>();
        // System.out.println("Initialisation explosion");
        for (Explosion explosion : explosions) {
            if (explosion.update(delta)) {
                explosionsToRemove.add(explosion); // Explosion terminée
                // System.out.println("ajoute une explosion");
            }
            System.out.println("Explosion render");
            explosion.render(batch);
            Rectangle explosionArea = explosion.getBounds(); // Assuming Explosion has a getBounds() method
            //handleExplosion(explosionArea);
        }
        // System.out.println("remove explosion");
        explosions.removeAll(explosionsToRemove);
    }

    public void handleExplosionImpact(List<Rectangle> affectedAreas) {
        this.explosionAreas = affectedAreas;
    }

    public boolean isSolidWall(int cellX, int cellY) {
        return gameMap.isSolidWall(cellX, cellY, false);
    }

    // Méthode pour vérifier si le jeu est terminé
    public boolean isGameOver() {
        return gameOver;
    }

    public void endGame(CatPlayer loser) {
        this.loser = loser;
        for (CatPlayer player : players) {
            if (player != loser) {
                this.winner = player;
                break;
            }
        }
        gameOver = false;
        transitioningToGameOver = true;
        gameOverDelay = 2.0f;
        System.out.println("Game Over! " + loser + "lost." + winner + "wins.");
        loser.setLoser(true);
    }


    public void restartGame() {
        System.out.println("Restarting game...");
        main.create();
        //((Game)main).setScreen(new GameScreen()); // Réinitialise l'écran
    }

    // private void handlePlayerInput(CatPlayer player, float delta) {
    //     float speed = 200 * delta;
    
    //     if (player == players.get(0)) { // Joueur 1
    //         if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.move(0, speed);
    //         if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.move(0, -speed);
    //         if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.move(-speed, 0);
    //         if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.move(speed, 0);
    //         if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
    //             player.placeYarnBall(this);
    //             System.out.println("Player 1 placed a yarn ball");
    //         }
    //     } else if (player == players.get(1)) { // Joueur 2
    //         if (Gdx.input.isKeyPressed(Input.Keys.Z)) player.move(0, speed);
    //         if (Gdx.input.isKeyPressed(Input.Keys.S)) player.move(0, -speed);
    //         if (Gdx.input.isKeyPressed(Input.Keys.Q)) player.move(-speed, 0);
    //         if (Gdx.input.isKeyPressed(Input.Keys.D)) player.move(speed, 0);
    //         if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
    //             player.placeYarnBall(this);
    //             System.out.println("Player 2 placed a yarn ball");
    //         }
    //     }
    // }

    // private void handlePlayerInput(CatPlayer player, int playerIndex, float delta) {
    //     float speed = 200 * delta;
    
    //     if (playerIndex == 0) { // Joueur 1
    //         if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.move(0, speed);
    //         if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.move(0, -speed);
    //         if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.move(-speed, 0);
    //         if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.move(speed, 0);
    //         if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
    //             player.placeYarnBall(this);
    //             System.out.println("Player 1 placed a yarn ball");
    //         }
    //     }

    //     if (playerIndex == 1) { // Joueur 2
    //         if (Gdx.input.isKeyPressed(Input.Keys.W)) player.move(0, speed);
    //         if (Gdx.input.isKeyPressed(Input.Keys.S)) player.move(0, -speed);
    //         if (Gdx.input.isKeyPressed(Input.Keys.A)) player.move(-speed, 0);
    //         if (Gdx.input.isKeyPressed(Input.Keys.D)) player.move(speed, 0);
    //         if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
    //             player.placeYarnBall(this);
    //             System.out.println("Player 2 placed a yarn ball");
    //         }
    //     }
    // }
    

    private void updatePlayerCollisions(CatPlayer player, float dx, float dy) {
        // float dx = player.getBounds().x;
        // float dy = player.getBounds().y;
    
        // Vérification des collisions avec les murs
        boolean collided = false;
        for (Wall wall : walls) {
            if (player.getBounds().overlaps(wall.getBounds())) {
                player.move(-dx, -dy);
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
        
    
        // Si collision détectée, annulez le déplacement
        if (collided) {
            player.move(-dx, -dy);
        }


        // Si une collision avec une boule de laine, annulez le déplacement
        if (collidedWithYarnBall) {
            player.move(-dx, -dy);
        }
    }
    
    
    
    
    // _________________ OVERRIDE _________________

    // ========== Show ==========
    @Override
    public void show() {}

    
    // ========== Render ==========
    @Override
    public void render(float delta) {
        update(delta); // Appel de la méthode de mise à jour (si nécessaire)

        Gdx.gl.glClearColor(0.0f, 0.3f, 0.0f, 1.0f); // Définit une couleur de fond vert mais qui finit blanche xD
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Efface l'écran

        //camera.position.set(400, 300, 0);
        camera.position.set((GameMap.getTotalCols() * GameMap.getCellWidth()) / 2f, (GameMap.getTotalRows() * GameMap.getCellHeight()) / 2f, 0);
        camera.update(); // Met à jour la caméra
        batch.setProjectionMatrix(camera.combined); // Définit la matrice de projection
        

        if (transitioningToGameOver) {
            gameOverDelay -= delta;
            if (gameOverDelay <= 0) {
                gameOver = true;
                transitioningToGameOver = false;
            }
        }

        if (gameOver) {
            // Efface l'écran
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glClearColor(0.0f, 0.7f, 0.0f, 1.0f);
            batch.begin();
            batch.draw(gameOverBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Texture en plein écran
            // Affiche le message Game Over
            BitmapFont font = new BitmapFont();
            font.getData().setScale(2);
            font.draw(batch, "Game Over!", 300, 500);
             // Affiche qui a perdu
            font.draw(batch, "Player Lost: " + (loser == players.get(0) ? "Player 1" : "Player 2"), 300, 400);

            // Affiche le gagnant
            font.draw(batch, "Winner: " + (winner == players.get(0) ? "Player 1" : "Player 2"), 300, 350);
            font.draw(batch, "Press R to Restart", 300, 250);
            font.draw(batch, "Press Q to Quit", 300, 200);
            batch.end();

            if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                System.out.println("Restart ou pas !");
                restartGame();
                return;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)){
                Gdx.app.exit();
            }
            return; // Arrête le rendu normal
        }

        batch.begin();

        // Rendu des murs
        for (Wall wall : walls) {
            wall.render(batch);
        }

        // Rendu des joueurs et des balles de laine posées
        for (CatPlayer currentPlayer : players) {
            currentPlayer.render(batch);
        }

        // Rendu des YarnBallUp
        for (YarnBallUp yarnBallUp : gameMap.getYarnBallUps()) {
            yarnBallUp.render(batch);
        }

        // Rendu des YarnBallPower
        for (YarnBallPower yarnBallPower : gameMap.getYarnBallPowers()) {
            yarnBallPower.render(batch);
        }

        for (Mouse Mouse: gameMap.getMice()) {
            Mouse.render(batch);
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
        for (CatPlayer player : players) {
            if (player != null && player.getTexture() != null) {
                player.getTexture().dispose();
            }
        }


        shapeRenderer.dispose();

    }


    // ================ Update ===========================
    private void update(float delta) {
        
        if (players.isEmpty()) return;
        for (int i = 0; i < players.size(); i++) {
            CatPlayer player = players.get(i);
            float speed = 100 * delta;
            float dx = 0, dy = 0;
            //handlePlayerInput(player, i, delta);
            //updatePlayerCollisions(player, delta); // Vérification des collisions
        
            if (i == 0) { // Joueur 1
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) dy += speed;
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) dy -= speed;
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) dx -= speed;
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dx += speed;
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    player.placeYarnBall(this);
                    System.out.println("Player 1 placed a yarn ball");
                }
            } else if (i == 1) { // Joueur 2
                if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += speed;
                if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= speed;
                if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= speed;
                if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += speed;
                if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                    player.placeYarnBall(this);
                    System.out.println("Player 2 placed a yarn ball");
                }
            }

            // Déplace le joueur et vérifie les collisions
            player.move(dx, dy);
            updatePlayerCollisions(player, dx, dy);

            // Mise à jour des balles de laine
            player.update(delta);

                        // Mettez à jour les souris
            // for (Mouse mouse : gameMap.getMice()) {
            //     mouse.update(delta); // Vérifie les boosts actifs
            // }
        }


        // Vérifiez les collisions avec YarnBallUp
        List<YarnBallUp> itemsToRemoveUps = new ArrayList<>();
        for (YarnBallUp yarnBallUp : gameMap.getYarnBallUps()) {
            for (CatPlayer player : players) {
                if (player.getBounds().overlaps(yarnBallUp.getBounds())) {
                    itemsToRemoveUps.add(yarnBallUp);
                    player.increaseMaxYarnBalls();
                }
            }
        }

        // Gérer les collisions avec YarnBallPower
        List<YarnBallPower> itemsToRemovePowers = new ArrayList<>();
        for (YarnBallPower yarnBallPower : gameMap.getYarnBallPowers()) {
            for (CatPlayer player : players) {
                if (player.getBounds().overlaps(yarnBallPower.getBounds())) {
                    itemsToRemovePowers.add(yarnBallPower);
                    player.increaseMaxYarnBallsPower();
                }
            }
        }



        // Gérer les collisions avec les souris
        List<Mouse> itemsToRemoveMice = new ArrayList<>();
        for (Mouse mouse : gameMap.getMice()) {
            for (CatPlayer player : players) {
                if (player.getBounds().overlaps(mouse.getBounds())) {
                    mouse.activate(player);
                    itemsToRemoveMice.add(mouse); // Mark the mouse item for removal
                    System.out.println("Collected Mouse! Speed Boost! " + ((CatPlayer) player).getSpeed());
                }
            
            }
        }
        
        //gameMap.checkMouseCollisions((CatPlayer) gameMap.getPlayer());


        // Retirez les items ramassés
        gameMap.getYarnBallUps().removeAll(itemsToRemoveUps);
        gameMap.getYarnBallPowers().removeAll(itemsToRemovePowers);
        gameMap.getMice().removeAll(itemsToRemoveMice);


    }
    
}