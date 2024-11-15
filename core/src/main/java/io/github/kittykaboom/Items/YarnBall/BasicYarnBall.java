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
}