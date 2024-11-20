package io.github.kittykaboom.Items.Special;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.Functionality.TextureManager;
import io.github.kittykaboom.Players.CatPlayer;

public abstract class Special {
    protected Texture texture;
    protected Rectangle bounds;

    // Constructor
    public Special(String texturePath, float x, float y, float width, float height) {
        this.texture = TextureManager.getTexture(texturePath);
        this.bounds = new Rectangle(x, y, width, height);
    }

    // Abstract methods to be implemented by subclasses
    public abstract void applyEffect(CatPlayer player);

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}

