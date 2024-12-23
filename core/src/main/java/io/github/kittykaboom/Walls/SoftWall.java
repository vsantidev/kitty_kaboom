package io.github.kittykaboom.Walls;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.Functionality.TextureManager;
import io.github.kittykaboom.GameMap;

public class  SoftWall extends Wall {
    //private Sprite wallSprite;
    //private Rectangle bounds;
    //private boolean isDestroyed = false; // Tracks if the wall is destroyed

    public SoftWall(float x, float y) {
        // super(x, y);
        // this.texture = new Texture("textures/soft_wall.png");
        super(x, y, GameMap.getCellWidth(), GameMap.getCellHeight());
        this.texture =  TextureManager.getTexture("textures/soft_wall.png");

        // Initialize sprite with texture and position
        // wallSprite = new Sprite(this.texture);
        // wallSprite.setPosition(x, y);

        // Set the sprite size based on cell dimensions
        // wallSprite.setSize(GameMap.getCellWidth(), GameMap.getCellHeight());

        // Define collision bounds for the wall
        // this.bounds = new Rectangle(x, y, GameMap.getCellWidth(), GameMap.getCellHeight());
    }

    // @Override
    // public void setTexture(Texture texture) {
    //     if (this.texture != null) {
    //         this.texture.dispose();
    //     }
    //     this.texture = texture;
    //     this.wallSprite.setTexture(texture);
    // }

    // @Override
    // public Texture getTexture() {
    //     return texture;
    // }

    // @Override
    // public void render(SpriteBatch batch) {
    //     if (!isDestroyed) {
    //         wallSprite.draw(batch);
    //     }
    // }

    // @Override
    // public Rectangle getBounds() {
    //     return isDestroyed ? null : bounds; // No bounds if the wall is destroyed
    // }

    // public void destroy() {
    //     isDestroyed = true;
    // }

    // public boolean isDestroyed() {
    //     return isDestroyed;
    // }


    @Override
    public void setTexture(Texture texture) {
        if (this.texture != null) {
            this.texture.dispose();
        }
        this.texture = texture;
    }

    @Override
    public boolean isDestructible() {
        return true;
    }

    // @Test
    // public void testSoftWallDestruction() {
    //     SoftWall softWall = new SoftWall(50, 50);
    //     Rectangle explosionArea = new Rectangle(50, 50, 50, 46);
    //     assertTrue(explosionArea.overlaps(softWall.getBounds()), "Explosion should overlap with SoftWall");
    // }
}
