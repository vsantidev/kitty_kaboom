package io.github.kittykaboom.Players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Player {
    protected Texture texture;
    protected Vector2 position;
    protected Rectangle bounds;

    public Player(float x, float y) {
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x, y, 50, 50);
    }

    public void move(float dx, float dy) {
        position.add(dx, dy);
        bounds.setPosition(position.x, position.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }


    public Texture getTexture() {
        return texture;
    }

    public abstract void setTexture(Texture texture);
}
