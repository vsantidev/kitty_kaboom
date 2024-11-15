package io.github.kittykaboom.Items.YarnBall;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.kittykaboom.Players.CatPlayer;

public class YarnBallUp {
    private Texture texture;
    private float x, y;


    //____________ CONSTRUCTOR ____________
    public YarnBallUp(float x, float y) {
        this.texture = new Texture("textures/yarn_ball_up.png");
        this.x = x;
        this.y = y;
    }


    //____________ METHODS ____________
    public void applyEffect(CatPlayer player) {
        player.increaseMaxYarnBalls();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, 50, 50);
    }
}
