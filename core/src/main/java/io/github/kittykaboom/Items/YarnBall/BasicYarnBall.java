package io.github.kittykaboom.Items.YarnBall;

import io.github.kittykaboom.GameScreen;

public class BasicYarnBall extends YarnBall {
    private GameScreen gameScreen;

    //____________ CONSTRUCTOR ____________
    public BasicYarnBall(float x, float y, GameScreen gameScreen) {
        super(x, y, "textures/basic_yarn_ball.png");
        this.gameScreen = gameScreen;
    }

    //____________ METHODS ____________
    @Override
    public void explode(){
        System.out.println("Basic YarnBall explodes!");
        gameScreen.addExplosion(bounds.x, bounds.y);
        // Logique d'explosion ici : inflige des dégâts, déclenche des effets, etc.
    }

    // @Override
    // public void explode() {
    //     // Liste des positions touchées (nord, sud, est, ouest)
    //     List<Rectangle> affectedArea = new ArrayList<>();

    //     // Taille de chaque cellule
    //     int cellWidth = GameMap.getCellWidth();
    //     int cellHeight = GameMap.getCellHeight();

    //     // Calcul des zones affectées
    //     affectedArea.add(new Rectangle(bounds.x, bounds.y + cellHeight, cellWidth, cellHeight)); //Nord
    //     affectedArea.add(new Rectangle(bounds.x, bounds.y - cellHeight, cellWidth, cellHeight)); // Sud
    //     affectedArea.add(new Rectangle(bounds.x + cellWidth, bounds.y, cellWidth, cellHeight)); // Est
    //     affectedArea.add(new Rectangle(bounds.x - cellWidth, bounds.y, cellWidth, cellHeight)); // Ouest   
        
    //     // Notifier GameScreen pour gérer les effets
    //     //gameScreen.handleExplosionImpact(affectedArea, this);
    // }

}