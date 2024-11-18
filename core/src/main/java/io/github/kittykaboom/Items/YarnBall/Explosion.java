package io.github.kittykaboom.Items.YarnBall;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.GameMap;

public class Explosion {
    private Rectangle bounds; // For collision checks
    private Texture texture;  // Explosion texture
    private float timer;      // Explosion's duration timer
    private static final float DURATION = 1f; // Explosion lasts for 1 second

    public Explosion(float x, float y) {
        // Set bounds for explosion area
        this.bounds = new Rectangle(x, y, GameMap.getCellWidth(), GameMap.getCellHeight());
        this.texture = new Texture("textures/explosion.png");
        this.timer = DURATION;
    }

    // Returns the bounds of the explosion for collision detection
    public Rectangle getBounds() {
        return bounds;
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
