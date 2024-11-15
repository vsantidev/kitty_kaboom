package io.github.kittykaboom.Players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.GameMap;

public class CatPlayer extends Player {
    private Sprite playerSprite;
    private Rectangle bounds;

    public CatPlayer(float x, float y) {
        super(x, y);
        this.texture = new Texture("textures/cat.png");
        
        playerSprite = new Sprite(this.texture);
        playerSprite.setPosition(x, y);

         // Réduisez la taille du sprite et des limites pour éviter les collisions excessives
         int adjustedWidth = GameMap.getCellWidth() - 5;  // 10 pixels de moins en largeur
         int adjustedHeight = GameMap.getCellHeight() - 5; // 10 pixels de moins en hauteur
 
         this.playerSprite.setSize(adjustedWidth, adjustedHeight);
         this.bounds = new Rectangle(x, y, adjustedWidth, adjustedHeight);
    }

    public void move(float dx, float dy) {
        playerSprite.translate(dx, dy);
        bounds.setPosition(playerSprite.getX(), playerSprite.getY()); // Mettez à jour les limites
    }

    public Rectangle getBounds(){
        return  bounds;
    }

    @Override
    public void setTexture(Texture texture) {
        if (this.texture != null) {
            this.texture.dispose();
        }
        this.texture = texture;
        this.playerSprite.setTexture(texture);
    }

    @Override
    public void render(SpriteBatch batch) {
        playerSprite.draw(batch);
    }
}

