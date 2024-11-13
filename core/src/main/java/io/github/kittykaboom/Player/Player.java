package io.github.kittykaboom.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private TextureRegion texture;
    private Vector2 position;
    private Rectangle bounds;

    public Player(float x, float y) {
        // Texture temp = new Texture("textures/player.jpg");
        // texture = new TextureRegion(temp, 520, 540, 50, 50);
        Texture temp = new Texture("textures/cat.png");
        texture = new TextureRegion(temp, 0, 0, 50, 50);
        position = new Vector2(x, y);
        bounds = new Rectangle(x, y, 50, 50);
    }

    public void update(float delta) {
        // Logique de mise à jour du joueur, par exemple pour le mouvement
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
}

