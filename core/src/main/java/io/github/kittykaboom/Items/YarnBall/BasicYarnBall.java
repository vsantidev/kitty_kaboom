package io.github.kittykaboom.Items.YarnBall;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.GameMap;
import io.github.kittykaboom.GameScreen;
import io.github.kittykaboom.Players.CatPlayer;

public class BasicYarnBall extends YarnBall {
    private GameScreen gameScreen;
    private CatPlayer owner;

    //____________ CONSTRUCTOR ____________
    public BasicYarnBall(float x, float y, GameScreen gameScreen, CatPlayer owner) {
        super(x, y, "textures/basic_yarn_ball.png");
        this.gameScreen = gameScreen;
        this.owner = owner;
    }

    //____________ METHODS ____________

    @Override
    public void explode() {
        // if (player == null) {
        //     System.out.println("Error: Player is null in explode!");
        //     return;
        // }
        // Liste des positions touchées (nord, sud, est, ouest)
        
        if (gameScreen.getPlayers() == null || gameScreen.getPlayers().isEmpty()) {
            System.out.println("Error: No players in game!");
            return;
        }
        
        List<Rectangle> affectedArea = new ArrayList<>();

        // Taille de chaque cellule
        int cellWidth = GameMap.getCellWidth();
        int cellHeight = GameMap.getCellHeight();

        // Calcul des indices de cellule actuels
        int currentCellX = (int) (bounds.x / cellWidth);
        int currentCellY = GameMap.getTotalRows() - 1 - (int) (bounds.y / cellHeight);

        gameScreen.addExplosion(bounds.x, bounds.y);
        affectedArea.add(new Rectangle(bounds.x, bounds.y, cellWidth, cellHeight)); //Centre
    
        // Récupère la portée maximale du joueur
        int maxPower = owner.getMaxYarnBallsPower();


        // for (CatPlayer player : gameScreen.getPlayers()) {
        //     for (Rectangle area : affectedArea) {
        //         if (player.getBounds().overlaps(area)) {
        //             System.out.println("Player hit by explosion: " + player);
        //             gameScreen.endGame();
        //             return;
        //         }
        //     }
        // }

        // _____________ Première étape d'explosion _____________
        // Vérifie chaque direction pour les murs avant d'ajouter les zones affectées
        // if (!gameScreen.isSolidWall(currentCellX, currentCellY + 1)) { // Nord
        //     System.out.println("Trouvé Kaboom");
        //     gameScreen.addExplosion(bounds.x, bounds.y + cellHeight);
        //     affectedArea.add(new Rectangle(bounds.x, bounds.y + cellHeight, cellWidth, cellHeight));
        // } else {
        //     System.out.println("Pas Kaboom");
        //     //gameScreen.addExplosion(bounds.x, bounds.y + cellHeight);
        // }
        // if (!gameScreen.isSolidWall(currentCellX, currentCellY - 1)) { // Sud
        //     affectedArea.add(new Rectangle(bounds.x, bounds.y - cellHeight, cellWidth, cellHeight));
        //     gameScreen.addExplosion(bounds.x, bounds.y - cellHeight);
        // }
        // if (!gameScreen.isSolidWall(currentCellX + 1, currentCellY)) { // Est
        //     affectedArea.add(new Rectangle(bounds.x + cellWidth, bounds.y, cellWidth, cellHeight));
        //     gameScreen.addExplosion(bounds.x + cellWidth, bounds.y);
        // }
        // if (!gameScreen.isSolidWall(currentCellX - 1, currentCellY)) { // Ouest
        //     gameScreen.addExplosion(bounds.x - cellWidth, bounds.y);
        //     affectedArea.add(new Rectangle(bounds.x - cellWidth, bounds.y, cellWidth, cellHeight));
        // }

        // _____________ Deuxième étape d'explosion _____________
        // Explosion multi-directionnelle
        // for (int power = 1; power <= maxPower; power++) {
        //     if (!gameScreen.isSolidWall(currentCellX, currentCellY + power)) { // Nord
        //         gameScreen.addExplosion(bounds.x, bounds.y + power * cellHeight);
        //         affectedArea.add(new Rectangle(bounds.x, bounds.y + power * cellHeight, cellWidth, cellHeight));
        //     }
        //     if (!gameScreen.isSolidWall(currentCellX, currentCellY - power)) { // Sud
        //         gameScreen.addExplosion(bounds.x, bounds.y - power * cellHeight);
        //         affectedArea.add(new Rectangle(bounds.x, bounds.y - power * cellHeight, cellWidth, cellHeight));
        //     }
        //     if (!gameScreen.isSolidWall(currentCellX + power, currentCellY)) { // Est
        //         gameScreen.addExplosion(bounds.x + power * cellWidth, bounds.y);
        //         affectedArea.add(new Rectangle(bounds.x + power * cellWidth, bounds.y, cellWidth, cellHeight));
        //     }
        //     if (!gameScreen.isSolidWall(currentCellX - power, currentCellY)) { // Ouest
        //         gameScreen.addExplosion(bounds.x - power * cellWidth, bounds.y);
        //         affectedArea.add(new Rectangle(bounds.x - power * cellWidth, bounds.y, cellWidth, cellHeight));
        //     }
        // }

        // _____________ Troisième étape d'explosion _____________

        // Explosion vers le Nord
        for (int power = 1; power <= maxPower; power++) {
            if (gameScreen.getGameMap().isSolidWall(currentCellX, currentCellY + power, true)) {
                break; // Stoppe l'explosion au mur solide
            }

            // Ajoute l'explosion si pas de mur
            gameScreen.addExplosion(bounds.x, bounds.y + power * cellHeight);
            affectedArea.add(new Rectangle(bounds.x, bounds.y + power * cellHeight, cellWidth, cellHeight));
        }

        // Explosion vers le Sud
        for (int power = 1; power <= maxPower; power++) {
            if (gameScreen.getGameMap().isSolidWall(currentCellX, currentCellY - power, true)) {
                break;
            }
            gameScreen.addExplosion(bounds.x, bounds.y - power * cellHeight);
            affectedArea.add(new Rectangle(bounds.x, bounds.y - power * cellHeight, cellWidth, cellHeight));
        }
    
        // Explosion vers l'Est
        for (int power = 1; power <= maxPower; power++) {
            if (gameScreen.getGameMap().isSolidWall(currentCellX + power, currentCellY, true)) {
                break;
            }
            gameScreen.addExplosion(bounds.x + power * cellWidth, bounds.y);
            affectedArea.add(new Rectangle(bounds.x + power * cellWidth, bounds.y, cellWidth, cellHeight));
        }
    
        // Explosion vers l'Ouest
        for (int power = 1; power <= maxPower; power++) {
            if (gameScreen.getGameMap().isSolidWall(currentCellX - power, currentCellY, true)) {
                break;
            }
            gameScreen.addExplosion(bounds.x - power * cellWidth, bounds.y);
            affectedArea.add(new Rectangle(bounds.x - power * cellWidth, bounds.y, cellWidth, cellHeight));

        }

        // Vérifie si le chat est touché
        /*if (gameScreen.getGameMap().isPlayerHit(affectedArea)) {
            System.out.println("Game Over! The player was hit by the explosion.");
            gameScreen.endGame(); // Terminer la partie
        }*/
        for (CatPlayer player : gameScreen.getPlayers()) {
            for (Rectangle area : affectedArea) {
                if (player.getBounds().overlaps(area)) {
                    System.out.println("Player hit by explosion: " + player);
                    gameScreen.endGame(player);
                    return;
                }
            }
        }


        // Envoyer les zones affectées à GameScreen pour afficher les bordures
        gameScreen.handleExplosionImpact(affectedArea);

        // Afficher un log pour vérifier l'explosion
        System.out.println("Explosion effect applied!");
    }

    // @Test
    // public void testYarnBallPlacement() {
    //     CatPlayer player = new CatPlayer(100, 100, "textures/cat_one.png");
    //     GameMap gameMap = new GameMap("map.txt");
    //     player.placeYarnBall(gameMap);
    //     assertEquals(1, player.getYarnBalls().size(), "Player should place one yarn ball");
    // }

}