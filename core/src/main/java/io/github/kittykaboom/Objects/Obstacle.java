package io.github.kittykaboom.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Obstacle {
    private TextureRegion texture;
    private Vector2 position;
    private Rectangle bounds;

    public Obstacle(String texturePath, float x, float y) {
        Texture temp = new Texture(texturePath);
        texture = new TextureRegion(temp, 25, 80, 55, 55);
        position = new Vector2(x, y);
        bounds = new Rectangle(x, y, 50, 50);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }
}
