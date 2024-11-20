package io.github.kittykaboom.Items.Special;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.Functionality.TextureManager;
import io.github.kittykaboom.GameMap;
import io.github.kittykaboom.Players.CatPlayer;

public class Mouse {
    private Texture texture;
    private Rectangle bounds;
    private float x, y;
    private float speedBoost = 100; // Speed increase for the CatPlayer
    private float duration = 5; // Duration of the speed boost
    private boolean isActive = false;
    private float activeTime = 0;

    //____________ CONSTRUCTOR ____________
    public Mouse(float x, float y) {
        this.texture = TextureManager.getTexture("textures/mouse.png");
        this.bounds = new Rectangle(x, y, GameMap.getCellWidth(), GameMap.getCellHeight());
    }

    //____________ METHODS ____________

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void activate(CatPlayer catPlayer) {
        if (!isActive) {
            catPlayer.setSpeed(catPlayer.getSpeed() + speedBoost);
            isActive = true;
        }
    }

    public void update(float delta, CatPlayer catPlayer) {
        if (isActive) {
            activeTime += delta;
            if (activeTime >= duration) {
                catPlayer.setSpeed(catPlayer.getSpeed() - speedBoost);
                isActive = false;
                activeTime = 0;
            }
        }
    }
    public void dispose() {
        texture.dispose();
    }
}
    
