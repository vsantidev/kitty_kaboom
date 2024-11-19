package io.github.kittykaboom.Items.Special;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.Functionality.TextureManager;
import io.github.kittykaboom.GameMap;

public class YarnBallUp {
    private Texture texture;
    private Sprite sprite;
    private Rectangle bounds;
    private float x, y;


    //____________ CONSTRUCTOR ____________
    public YarnBallUp(float x, float y) {
        //this.texture = new Texture("textures/yarn_ball_up.png");
        // this.x = x;
        // this.y = y;
        // this.sprite = new Sprite(texture);
        // this.sprite.setPosition(x, y);

        // int width = 30;
        // int height = 30;

        // this.sprite.setSize(width, height);
        // this.bounds = new Rectangle(x, y, width, height);
        this.texture = TextureManager.getTexture("textures/yarn_ball_up.png");
        this.bounds = new Rectangle(x, y, GameMap.getCellWidth(), GameMap.getCellHeight());
   
   
    }


    //____________ METHODS ____________
    // public void applyEffect(CatPlayer player) {
    //     player.increaseMaxYarnBalls();
    // }

    // public void render(SpriteBatch batch) {
    //     batch.draw(texture, x, y, 50, 50);
    // }

    // public void render(SpriteBatch batch) {
    //     sprite.draw(batch); // Dessiner l’item
    // }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height); // Dessiner l’item
    }

    public Rectangle getBounds() {
        return bounds; // Retourner les limites pour les collisions
    }

    public void dispose() {
        // sprite.getTexture().dispose(); // Libérer les ressources
    }
}
