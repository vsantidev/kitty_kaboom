package io.github.kittykaboom;

import java.util.ArrayList;
import java.util.Iterator;
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
import io.github.kittykaboom.Walls.SoftWall;
import io.github.kittykaboom.Walls.Wall;

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private ShapeRenderer shapeRenderer;
    private Main main;
    private Texture gameOverBackground;
    private Texture arrowRight;
    private Texture arrowLeft;
    private Texture arrowUp;
    private Texture arrowDown;
    private Texture mouse;
    private Texture ballFire;
    private Texture ballUp;
    private Texture wallsoft;
    private BitmapFont font;
    private Texture trophyTexture;

    private static final int LEFT_MARGIN = 300; // Espace pour le texte du joueur 1
    private static final int RIGHT_MARGIN = 300; // Espace pour le texte du joueur 2


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
        int viewportWidth = (GameMap.getTotalCols() * GameMap.getCellWidth()) + LEFT_MARGIN + RIGHT_MARGIN;
        int viewportHeight = (GameMap.getTotalRows() * GameMap.getCellHeight());
        viewport = new FitViewport(viewportWidth, viewportHeight, camera);
        batch = new SpriteBatch();
        gameOverBackground = new Texture("textures/trophe.png"); // Texture personnalisée
        arrowRight = new Texture("textures/direction_arrow_right.png");
        arrowLeft = new Texture("textures/direction_arrow_left.png");
        arrowUp = new Texture("textures/direction_arrow_top.png");
        arrowDown = new Texture("textures/direction_arrow_bottom.png");
        ballUp = new Texture("textures/yarn_ball_up.png");
        ballFire = new Texture("textures/yarn_ball_power.png");
        mouse = new Texture("textures/mouse.png");
        wallsoft = new Texture("textures/soft_wall.png");


        // Initialise la carte
        gameMap = new GameMap("map.txt");
        walls = gameMap.getWalls();
        shapeRenderer = new ShapeRenderer();
        players = gameMap.getPlayers();
    
        // Initialisation du BitmapFont
        font = new BitmapFont();
        font.getData().setScale(1.5f); // Ajuste la taille du texte
    }
    


    // _________________ GETTERS & SETTERS _________________

    public GameMap getGameMap() {
        return gameMap;
    }

    public List<CatPlayer> getPlayers() {
        return players;
    }

    private boolean isChanceSuccessful(double probability) {
        return Math.random() < probability; // Retourne true si l'événement se produit
    }

    // _________________ METHODS _________________

    public void addExplosion(float x, float y) {
        explosions.add(new Explosion(x, y));
    }

    // private void renderExplosions(float delta, SpriteBatch batch) {
    //     List<Explosion> explosionsToRemove = new ArrayList<>();
    //     // System.out.println("Initialisation explosion");
    //     for (Explosion explosion : explosions) {
    //         if (explosion.update(delta)) {
    //             explosionsToRemove.add(explosion); // Explosion terminée
    //             // System.out.println("ajoute une explosion");
    //         }
    //         //System.out.println("Explosion render");
    //         explosion.render(batch);
    //         Rectangle explosionArea = explosion.getBounds(); // Assuming Explosion has a getBounds() method
    //         //handleExplosion(explosionArea);
    //         Iterator<Wall> wallIterator = walls.iterator();
    //     while (wallIterator.hasNext()) {
    //         Wall wall = wallIterator.next();
    //         if (wall instanceof SoftWall && explosionArea.overlaps(wall.getBounds())) {
    //             wallIterator.remove(); // Supprime le SoftWall
    //             handleWallDestruction((SoftWall) wall); // Gère l'apparition des items
    //             System.out.println("SoftWall destroyed at: " + wall.getBounds().x + ", " + wall.getBounds().y);
    //         }
    //     }
    //     }
    //     // System.out.println("remove explosion");
    //     explosions.removeAll(explosionsToRemove);
    // }

//     private void renderExplosions(float delta, SpriteBatch batch) {
//         List<Explosion> explosionsToRemove = new ArrayList<>();
    
//         for (Explosion explosion : explosions) {
//             if (explosion.update(delta)) {
//                 explosionsToRemove.add(explosion); // Explosion terminée
//                 System.out.println("Explosion terminée");
//             }
//             explosion.render(batch);
    
//             // Vérifie si l'explosion touche un SoftWall
//             Rectangle explosionArea = explosion.getBounds();
//             Iterator<Wall> wallIterator = walls.iterator();
//             while (wallIterator.hasNext()) {
//                 Wall wall = wallIterator.next();
//                 System.out.println("Explosion area: " + explosionArea);
// System.out.println("Wall bounds: " + wall.getBounds());
//                 if (wall instanceof SoftWall && explosionArea.overlaps(wall.getBounds())) {
//                     handleWallDestruction((SoftWall) wall); // Appelez la méthode d'abord
//                     wallIterator.remove(); // Ensuite, supprimez le mur
//                     System.out.println("SoftWall destroyed!");
//                 }
//             }
            
//         }
    
//         explosions.removeAll(explosionsToRemove);
//     }

private void renderExplosions(float delta, SpriteBatch batch) {
    List<Explosion> explosionsToRemove = new ArrayList<>();

    for (Explosion explosion : explosions) {
        if (explosion.update(delta)) {
            explosionsToRemove.add(explosion); // Explosion terminée
        }
        explosion.render(batch);

        // Récupère la zone d'explosion
        Rectangle explosionArea = explosion.getBounds();
        // System.out.println("Explosion area: " + explosionArea.x + ", " + explosionArea.y +
        //     ", width: " + explosionArea.width + ", height: " + explosionArea.height);
        
        for (Wall wall : walls) {
            //System.out.println("Checking wall at: " + wall.getBounds().x + ", " + wall.getBounds().y +
            //   ", width: " + wall.getBounds().width + ", height: " + wall.getBounds().height);
        
            if (wall instanceof SoftWall && explosionArea.overlaps(wall.getBounds())) {
                System.out.println("Collision detected with SoftWall at: " 
                                   + wall.getBounds().x + ", " + wall.getBounds().y);
                handleWallDestruction((SoftWall) wall); // Appelle la méthode si collision
            }
        }
        
        // Ajuste la zone pour inclure une marge (optionnel)
        Rectangle adjustedExplosionArea = new Rectangle(
            explosionArea.x - 2, explosionArea.y - 2,
            explosionArea.width + 4, explosionArea.height + 2
        );

        // Parcourt les murs pour vérifier les collisions
        Iterator<Wall> wallIterator = walls.iterator();
        while (wallIterator.hasNext()) {
            Wall wall = wallIterator.next();
            // System.out.println("Checking wall at: " + wall.getBounds());

            // Vérifie si le mur est un SoftWall et si l'explosion le touche
            if (wall instanceof SoftWall && adjustedExplosionArea.overlaps(wall.getBounds())) {
                // System.out.println("Collision detected with SoftWall at: " 
                //                    + wall.getBounds().x + ", " + wall.getBounds().y);

                // Gère la destruction du mur et l'apparition des items
                handleWallDestruction((SoftWall) wall);

                // Supprime le mur
                wallIterator.remove();
            }
        }
    }

    // Supprime les explosions terminées
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


    private void updatePlayerCollisions(CatPlayer player, float dx, float dy) {
        // float dx = player.getBounds().x;
        // float dy = player.getBounds().y;
    
        //Vérification des collisions avec les murs
        boolean collided = false;
        for (Wall wall : walls) {
            if (player.getBounds().overlaps(wall.getBounds())) {
                player.move(-dx, -dy);
                break;
            }
        }
        

        // Iterator<Wall> wallIterator = walls.iterator();
        // while (wallIterator.hasNext()) {
        //     Wall wall = wallIterator.next();
        //     if (wall instanceof SoftWall && explosionBounds.overlaps(wall.getBounds())) {
        //         wallIterator.remove(); // Supprime le mur
        //         handleWallDestruction((SoftWall) wall); // Gère l'apparition des items
        //         System.out.println("SoftWall destroyed!");
        //         break;
        //     }
        // }

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

    private void handleWallDestruction(SoftWall wall) {
        System.out.println("SoftWall destroyed at: " + wall.getBounds().x + ", " + wall.getBounds().y);
    }

    // private void handleWallDestruction(SoftWall wall) {
    //     // Probabilité d'apparition d'un item (30%)
    //     double spawnProbability = 0.3;

    //     float x = wall.getBounds().x;
    //     float y = wall.getBounds().y;
    //     System.out.println("rentre dans le handWallDestruction");
    //     if (Math.random() < spawnProbability) {
    //         // Décide quel type d'item apparaîtra
    //         if (Math.random() < 0.5) {
    //             // 50% de chances pour YarnBallUp
    //             gameMap.getYarnBallUps().add(new YarnBallUp(x, y));
    //             System.out.println("YarnBallUp spawned at: " + x + ", " + y);
    //         } else {
    //             // 50% de chances pour YarnBallPower
    //             gameMap.getYarnBallPowers().add(new YarnBallPower(x, y));
    //             System.out.println("YarnBallPower spawned at: " + x + ", " + y);
    //         }
    //     }
    // }

    // Affichage commandes
    private void renderPlayerControls(SpriteBatch batch) {
        if (font == null) {
            font = new BitmapFont(); // Initialise si ce n'est pas déjà fait
            font.getData().setScale(1.5f);
        }
    
        // Coordonnées adaptées au monde
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
    
        // JOUEUR 2
        float leftMargin = 50; // Distance depuis le bord gauche
        
            // Texte principal pour le joueur 2
            font.setColor(1, 1, 1, 1); // Blanc
            font.draw(batch, "Joueur 2:\n\n" +
                    "Z = Haut\n" +
                    "S = Bas\n" +
                    "Q = Gauche\n" +
                    "D = Droite\n" +
                    "R = Balle de Laine",
                    leftMargin, worldHeight - 50);


        
        // Texte principal pour le joueur 2
        font.setColor(1, 1, 1, 1); // Blanc


            font.draw(batch, "Objets spéciaux:", 50, 290);
                 // Dessin des flèches avec le texte correspondant
            batch.draw(ballUp, 50, 210, 40, 40); // Flèche haut
            font.draw(batch, "= Nombre de balle", 100, 238);
        
            batch.draw(ballFire, 50, 160, 40, 40); // Flèche bas
            font.draw(batch, "= Puissance balle", 100, 185);
        
            batch.draw(mouse, 50, 110, 40, 40); // Flèche gauche
            font.draw(batch, "= Rapidité du joueur", 100, 135);

            batch.draw(wallsoft, 50, 60, 40, 40); // Flèche gauche
            font.draw(batch, "= mur destructible", 100, 85);
        
        // JOUEUR 1
        float rightMargin = worldWidth - 250;
            // Texte principal pour le joueur 1
            font.setColor(1, 1, 1, 1); // Blanc

            // Dessin des flèches avec le texte correspondant
            font.draw(batch, "Joueur 1:", 1100, 548);

            // Dessin des flèches avec le texte correspondant
            batch.draw(arrowUp, 1100, 478, 22, 20); // Flèche haut
            font.draw(batch, "= Haut", 1130, 498);
        
            batch.draw(arrowDown, 1100, 450, 22, 20); // Flèche bas
            font.draw(batch, "= Bas", 1130, 470);
        
            batch.draw(arrowLeft, 1100, 422, 22, 20); // Flèche gauche
            font.draw(batch, "= Gauche", 1130, 442);
        
            batch.draw(arrowRight, 1100, 394, 22, 20); // Flèche droite
            font.draw(batch, "= Droite", 1130, 414);

            font.draw(batch, "3 = Balle de Laine", 1100, 385);

    }
    
    // private void renderInstructions(SpriteBatch batch) {
    //     if (font == null) {
    //         font = new BitmapFont(); // Initialise la police si nécessaire
    //         font.getData().setScale(1.5f); // Ajuste la taille pour une meilleure lisibilité
    //     }
    
    //     font.setColor(1, 1, 1, 1); // Couleur blanche pour le texte
    
    //     // Texte des indications
    //     String instructions = "Balle +1 = Augmente le nombre de balle    |    Balle Fire = Augmente la puissance de la balle    |    Souris = Augmente la vitesse du joueur";
    
    //     // Mesure la largeur exacte du texte
    //     GlyphLayout layout = new GlyphLayout();
    //     layout.setText(font, instructions);
    
    //     float textWidth = layout.width; // Largeur du texte
    //     float textHeight = layout.height; // Hauteur du texte
    
    //     // Coordonnées pour centrer le texte
    //     float worldWidth = viewport.getWorldWidth(); // Largeur visible dans le monde
    //     float worldHeight = viewport.getWorldHeight(); // Hauteur visible dans le monde
    

    //     float zoneStartY = 0; // Bas de l'écran
    //     float zoneHeight = worldHeight * 0.1f; // 10 % de la hauteur visible
    
    //     // Centre vertical dans la zone verte foncée
    //     float textY = zoneStartY + (zoneHeight / 2f) + (textHeight / 2f);
    //     float textX = (worldWidth - textWidth) / 2f; // Centrage horizontal

    //     // Dessine les instructions centrées
    //     font.draw(batch, instructions, textX, textY);
    
    //     // DEBUG : Dessine un rectangle pour visualiser la zone verte foncée
    //     // shapeRenderer.setProjectionMatrix(camera.combined);
    //     // shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    //     // shapeRenderer.setColor(1, 0, 0, 1); // Rouge pour le débogage
    //     // shapeRenderer.rect(0, zoneStartY, worldWidth, zoneHeight); // Rectangle autour de la zone
    //     // shapeRenderer.end();
    // }
    
    
    
    
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
        camera.position.set(
            LEFT_MARGIN + (GameMap.getTotalCols() * GameMap.getCellWidth()) / 2f, // Centre horizontal avec LEFT_MARGIN
            (GameMap.getTotalRows() * GameMap.getCellHeight()) / 2f,              // Centre vertical
            0
        );
        
        
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
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Nettoie le tampon
            batch.begin();

            //Affiche l'image de fond
            //batch.draw(gameOverBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Texture en plein écran
            
            // Affiche le message Game Over
            //BitmapFont font = new BitmapFont();
            font.getData().setScale(2);
            // font.draw(batch, "Fin de jeu !", 300, 500);


            // Affiche le trophée au centre
            float trophyX = (Gdx.graphics.getWidth() - 200) / 2f; // Centré horizontalement
            float trophyY = (Gdx.graphics.getHeight()) / 2f; // Décalé vers le haut
            batch.draw(gameOverBackground, trophyX, trophyY, 200, 200); // Dessine le trophée


            // // Affiche le gagnant
            // font.draw(batch, "Gagnant: " + (winner == players.get(0) ? "Chat roux" : "Chat gris"), 300, 350);

            //  // Affiche qui a perdu
            // font.draw(batch, "Perdant: " + (loser == players.get(0) ? "Chat roux" : "Chat gris"), 300, 400);

            // Affiche le texte du gagnant
            //BitmapFont font = new BitmapFont();
            font.getData().setScale(2);
            font.setColor(1, 1, 1, 1); // Couleur blanche
            String winnerName = (winner == players.get(0) ? "Chat roux" : "Chat tigré");
            font.draw(batch, "Félicitations, " + winnerName + " !", trophyX - 50, trophyY - 50);

            
            // Affiche les images des joueurs
            // if (players.get(0) != null && players.get(0).getTexture() != null) {
            //     batch.draw(players.get(0).getTexture(), 150, 350, 50, 50);
            // }
            // if (players.get(1) != null && players.get(1).getTexture() != null) {
            //     batch.draw(players.get(0).getTexture(), 500, 350, 50, 50);
            // }

            // Instructions
            // font.draw(batch, "Press R to Restart", 300, 250);
            font.draw(batch, "Appuyer sur Q pour quitter le jeu", trophyX - 100 , trophyY - 175);
            batch.end();

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

        // Affiche les commandes des joueurs
        renderPlayerControls(batch);
        // renderInstructions(batch);
        renderExplosions(delta, batch);

        batch.end();
        // --- Vérification emplacement map ---
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            //shapeRenderer.setColor(0, 1, 0, 1); // Couleur rouge pour le contour

        // Rectangle autour de la carte
            // shapeRenderer.rect(
            //     LEFT_MARGIN, // Position X du bord gauche
            //     0,           // Position Y du bas de la carte
            //     GameMap.getTotalCols() * GameMap.getCellWidth(), // Largeur de la carte
            //     GameMap.getTotalRows() * GameMap.getCellHeight() - 1 // Hauteur de la carte
            // );

        shapeRenderer.end();

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

        if (font != null) {
            font.dispose(); // Libère la mémoire utilisée par le BitmapFont
        }

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
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_3)) {
                    player.placeYarnBall(this);
                    System.out.println("Player 1 placed a yarn ball");
                }
            } else if (i == 1) { // Joueur 2
                if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += speed;
                if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= speed;
                if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= speed;
                if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += speed;
                if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
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
        
        // Gestion du speed au bon player
        for (Mouse mouse : gameMap.getMice()) {
            for (CatPlayer player : players) {
                if (mouse.affectsPlayer(player)) {
                    mouse.update(delta);
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