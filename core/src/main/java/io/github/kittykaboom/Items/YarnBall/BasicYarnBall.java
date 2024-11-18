package io.github.kittykaboom.Items.YarnBall;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.GameMap;
import io.github.kittykaboom.GameScreen;

public class BasicYarnBall extends YarnBall {
    private GameScreen gameScreen;

    //____________ CONSTRUCTOR ____________
    public BasicYarnBall(float x, float y, GameScreen gameScreen) {
        super(x, y, "textures/basic_yarn_ball.png");
        this.gameScreen = gameScreen;
    }

    //____________ METHODS ____________
    // @Override
    // public void explode(){
    //     System.out.println("Basic YarnBall explodes!");
    //     gameScreen.addExplosion(bounds.x, bounds.y);
    //     // Logique d'explosion ici : inflige des dégâts, déclenche des effets, etc.
    // }

    @Override
    public void explode() {
        // Liste des positions touchées (nord, sud, est, ouest)
        List<Rectangle> affectedArea = new ArrayList<>();

        // Taille de chaque cellule
        int cellWidth = GameMap.getCellWidth();
        int cellHeight = GameMap.getCellHeight();

    // Calcul des indices de cellule actuels
    int currentCellX = (int) (bounds.x / cellWidth);
    int currentCellY = GameMap.getTotalRows() - 1 - (int) (bounds.y / cellHeight);

    gameScreen.addExplosion(bounds.x, bounds.y);
    // gameScreen.addExplosion(bounds.x, bounds.y + cellHeight);
   


    // Vérifie chaque direction pour les murs avant d'ajouter les zones affectées
    if (!gameScreen.isSolidWall(currentCellX, currentCellY + 1)) { // Nord
        System.out.println("Pas trouvé Kaboom");
        gameScreen.addExplosion(bounds.x, bounds.y + cellHeight);
        // affectedArea.add(new Rectangle(bounds.x, bounds.y + cellHeight, cellWidth, cellHeight));
    } else {
        System.out.println("trouvé Kaboom");
        //gameScreen.addExplosion(bounds.x, bounds.y + cellHeight);
    }
    if (!gameScreen.isSolidWall(currentCellX, currentCellY - 1)) { // Sud
    //     affectedArea.add(new Rectangle(bounds.x, bounds.y - cellHeight, cellWidth, cellHeight));
        gameScreen.addExplosion(bounds.x, bounds.y - cellHeight);
    }
    if (!gameScreen.isSolidWall(currentCellX + 1, currentCellY)) { // Est
        //affectedArea.add(new Rectangle(bounds.x + cellWidth, bounds.y, cellWidth, cellHeight));
        gameScreen.addExplosion(bounds.x + cellWidth, bounds.y);
    }
    if (!gameScreen.isSolidWall(currentCellX - 1, currentCellY)) { // Ouest
        gameScreen.addExplosion(bounds.x - cellWidth, bounds.y);
        //affectedArea.add(new Rectangle(bounds.x - cellWidth, bounds.y, cellWidth, cellHeight));
    }

        // Envoyer les zones affectées à GameScreen pour afficher les bordures
        gameScreen.handleExplosionImpact(affectedArea);

        // Afficher un log pour vérifier l'explosion
        System.out.println("Explosion effect applied!");
    }
    // @Override
    // public void explode(){
    //     System.out.println("Basic YarnBall explodes!");
    //     gameScreen.addExplosion(bounds.x, bounds.y);
    //     // Logique d'explosion ici : inflige des dégâts, déclenche des effets, etc.
    // }
}