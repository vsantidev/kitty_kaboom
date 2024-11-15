package io.github.kittykaboom.Items.YarnBall;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Explosion {
    private Texture texture;
    private float x, y;
    private float duration = 5f;


    public Explosion(float x, float y) {
        this.texture = new Texture("textures/explosion.png");
        this.x = x;
        this.y = y;
    }

    public boolean update(float delta) {
        duration -= delta;
        return duration <= 0;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, 50, 50);
    }
}
