package io.github.kittykaboom.Walls;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.GameMap;

public class SolidWall extends Wall {
    private Sprite wallSprite;
    private Rectangle bounds;

    public SolidWall(float x, float y) {
        super(x, y);
        this.texture = new Texture("textures/solid_wall.png");

        // Initialisation du Sprite avec la texture et positionnement
        wallSprite = new Sprite(this.texture);
        wallSprite.setPosition(x, y);

        // Applique une échelle pour réduire la taille d'affichage
        //wallSprite.setScale(0.6f, 0.5f); // 60% de largeur et 50% de hauteur
        wallSprite.setSize(GameMap.getCellWidth(), GameMap.getCellHeight()); // Utilisation des getters    }

        // Défini les limites du mur pour les collisions
        this.bounds = new Rectangle(x,y, GameMap.getCellWidth(), GameMap.getCellHeight());

    }

    public Rectangle getBounds(){
        return  bounds; // Retourne les limites pour les vérifications de collision
    }
    
    @Override
    public void setTexture(Texture texture) {
        if (this.texture != null) {
            this.texture.dispose();
        }
        this.texture = texture;
        this.wallSprite.setTexture(texture); // Applique la texture mise à jour au sprite
    }

    @Override
    public void render(SpriteBatch batch) {
        wallSprite.draw(batch); // Utilisation du Sprite pour dessiner le mur avec l'échelle appliquée
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
