package io.github.kittykaboom.Walls;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Wall {
    protected Texture texture;
    protected Rectangle bounds;

    public Wall(float x, float y) {
        this.bounds = new Rectangle(x, y, 0.5f, 0.5f); // Taille par défaut des murs
    }



    public Rectangle getBounds() {
        return bounds;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y);
    }

    public Texture getTexture() {
        return texture;
    }

    public abstract void setTexture(Texture texture);
}
