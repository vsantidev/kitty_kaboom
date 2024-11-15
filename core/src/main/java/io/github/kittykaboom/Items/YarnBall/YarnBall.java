package io.github.kittykaboom.Items.YarnBall;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.GameMap;

public abstract class YarnBall {
    private Sprite yarnSprite;
    private Rectangle bounds;


    //____________ COSNTRUCTOR ____________
    public YarnBall(float x, float y, String texturePath) {
        Texture yarnTexture = new Texture(texturePath);
        this.yarnSprite = new Sprite(yarnTexture);
        this.yarnSprite.setPosition(x,y);

        // Initialisez les limites pour la balle de laine
        int adjustedWidth = GameMap.getCellWidth();
        int adjustedHeight = GameMap.getCellHeight();

        this.yarnSprite.setSize(adjustedWidth, adjustedHeight);
        this.bounds = new Rectangle(x,y, adjustedWidth, adjustedHeight);
    }

    //____________ METHODS ABSTRACT____________
    public abstract void attack();

    public void render(SpriteBatch batch) {
        yarnSprite.draw(batch);
    }

    public Rectangle getBounds() {
        return bounds;
    }

}