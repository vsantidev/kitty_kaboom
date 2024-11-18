package io.github.kittykaboom.Walls;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Wall {
    protected Texture texture;
    protected Rectangle bounds;

    public Wall(float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height); // Taille par défaut des murs
    }


    public Rectangle getBounds() {
        return bounds;
    }

    // public void render(SpriteBatch batch) {
    //     batch.draw(texture, bounds.x, bounds.y);
    // }
    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }
    
    public Texture getTexture() {
        return texture;
    }

    public abstract void setTexture(Texture texture);

    public abstract boolean isDestructible();
}
