package io.github.kittykaboom.Items.Special;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.Functionality.TextureManager;
import io.github.kittykaboom.GameMap;

public class YarnBallPower {
    private Texture texture;
    private Rectangle bounds;
    private float x, y;


    //____________ CONSTRUCTOR ____________
    public YarnBallPower(float x, float y) {

        this.texture = TextureManager.getTexture("textures/yarn_ball_power.png");
        this.bounds = new Rectangle(x, y, GameMap.getCellWidth(), GameMap.getCellHeight());
    }

    //____________ METHODS ____________

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height); // Dessiner l’item
    }

    public Rectangle getBounds() {
        return bounds; // Retourner les limites pour les collisions
    }

    public void dispose() {

    }
}

