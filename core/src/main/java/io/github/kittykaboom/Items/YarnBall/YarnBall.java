package io.github.kittykaboom.Items.YarnBall;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.GameMap;

public abstract class YarnBall {
    protected Sprite yarnSprite;
    protected Rectangle bounds;
    protected float cooldown;

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
    
        this.cooldown = 5f;
    }

    //____________ GETTERS & SETTERS ____________
    public Rectangle getBounds() {
        return bounds;
    }

    //____________ METHODS ____________
    public boolean update(float delta) {
        cooldown -=delta;
        return cooldown <= 0; //Retourne True si le cooldown est écoulé
    }

    public void render(SpriteBatch batch) {
        yarnSprite.draw(batch);
    }


    //____________ METHODS ABSTRACT ____________
    // public abstract void attack();

    public abstract void explode();

}