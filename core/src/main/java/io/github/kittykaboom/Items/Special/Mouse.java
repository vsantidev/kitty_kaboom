package io.github.kittykaboom.Items.Special;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.kittykaboom.Players.CatPlayer;

public class Mouse extends Special {
    private float speedBoost;
    private float duration; // Duration in seconds
    private boolean isEffectActive;
    private float elapsedTime;

    // Constructor
    public Mouse(String texturePath, float x, float y, float width, float height, float speedBoost, float duration) {
        super(texturePath, x, y, width, height);
        this.speedBoost = speedBoost;
        this.duration = duration;
        this.isEffectActive = false;
        this.elapsedTime = 0;
    }

    @Override
    public void applyEffect(CatPlayer player) {
        if (!isEffectActive) {
            player.setSpeed(player.getSpeed() + speedBoost); // Increase speed
            isEffectActive = true;
            elapsedTime = 0; // Reset elapsed time
        }
    }

    public void update(float delta, CatPlayer player) {
        if (isEffectActive) {
            elapsedTime += delta;

            // Revert the speed boost after the duration ends
            if (elapsedTime >= duration) {
                player.setSpeed(player.getSpeed() - speedBoost); // Revert speed
                isEffectActive = false;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!isEffectActive) {
            super.render(batch); // Render the item only if it hasn't been collected
        }
    }
}
