package io.github.kittykaboom.Items.YarnBall;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.GameMap;

public class Explosion {
    private float x, y;
    private Rectangle bounds; // For collision checks
    private Texture texture;  // Explosion texture
    private float timer;      // Explosion's duration timer
    private static final float DURATION = 1f; // Explosion lasts for 1 second

    public Explosion(float x, float y) {
        this.x = x;
        this.y = y;
        // Définir les limites pour la zone d'explosion  
        this.bounds = new Rectangle(x, y, GameMap.getCellWidth(), GameMap.getCellHeight());      this.bounds = new Rectangle(x, y, GameMap.getCellWidth(), GameMap.getCellHeight());
        this.texture = new Texture("textures/explosion.png");
        this.timer = DURATION;
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    // Returns the bounds of the explosion for collision detection
    // public Rectangle getBounds() {
    //     return bounds;
    // }

    // Retourne les limites de l'explosion pour la détection des collisions
    public Rectangle getBounds() {
        // Retourne un rectangle aligné sur la grille
        return new Rectangle(
            Math.round(x / GameMap.getCellWidth()) * GameMap.getCellWidth(),
            Math.round(y / GameMap.getCellHeight()) * GameMap.getCellHeight(),
            GameMap.getCellWidth(),
            GameMap.getCellHeight()
        );
    }

    
    

    // Updates the explosion's duration and checks if it's finished
    public boolean update(float delta) {
        timer -= delta;
        return timer <= 0; // Returns true if the explosion should be removed
    }

    // Renders the explosion on the screen
    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    // Cleans up resources
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
