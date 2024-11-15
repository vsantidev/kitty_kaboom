package io.github.kittykaboom.Players;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.GameMap;
import io.github.kittykaboom.GameScreen;
import io.github.kittykaboom.Items.YarnBall.BasicYarnBall;
import io.github.kittykaboom.Items.YarnBall.YarnBall;

public class CatPlayer extends Player {
    private Sprite playerSprite;
    private Rectangle bounds;
    private List<YarnBall> yarnBalls;
    private int maxYarnBalls = 1;

    //____________ CONSTRUCTOR ____________
    public CatPlayer(float x, float y) {
        super(x, y);
        this.texture = new Texture("textures/cat.png");
        
        playerSprite = new Sprite(this.texture);
        playerSprite.setPosition(x, y);

        // Réduit la taille du sprite et des limites pour éviter les collisions excessives
        int adjustedWidth = GameMap.getCellWidth() - 5;  // 10 pixels de moins en largeur
        int adjustedHeight = GameMap.getCellHeight() - 5; // 10 pixels de moins en hauteur

        this.playerSprite.setSize(adjustedWidth, adjustedHeight);
        this.bounds = new Rectangle(x, y, adjustedWidth, adjustedHeight);
        
        // Initialise la liste de balle de laine
        this.yarnBalls = new ArrayList<>();
    }

    //____________ GETTERS & SETTERS ____________
    public Rectangle getBounds(){
        return  bounds;
    }

    public int getMaxYarnBalls(){
        return maxYarnBalls;
    }


    //____________ METHODS ____________    
    public void move(float dx, float dy) {
        playerSprite.translate(dx, dy);
        bounds.setPosition(playerSprite.getX(), playerSprite.getY()); // Met à jour les limites
    }


    public boolean placeYarnBall(GameScreen gameScreen) {
        if (yarnBalls.size() < maxYarnBalls) {
            // Créez une nouvelle balle de laine à la position actuelle du joueur
            YarnBall yarnBall = new BasicYarnBall(bounds.x, bounds.y, gameScreen);
            yarnBalls.add(yarnBall); // Ajoutez-la à la liste
            return true;
        }
        return false;
    }


    public void update(float delta) {
        // Liste temporaire pour les balles de laine à supprimer
        List<YarnBall> ballsToRemove = new ArrayList<>();

        for (YarnBall yarnBall : yarnBalls) {
            if (yarnBall.update(delta, bounds)) { // Si le cooldown est écoulé
                yarnBall.explode(); // Déclenche l'explosion
                ballsToRemove.add(yarnBall); // Marque pour suppression
            }
        }

        // Supprime les balles qui ont explosé
        yarnBalls.removeAll(ballsToRemove); 
    }


    public List<YarnBall> getYarnBalls() {
        return yarnBalls; // Retourne la liste des balles de laine posées
    }


    public void increaseMaxYarnBalls() {
        maxYarnBalls++;
    }


    //____________ SETTER ABSTRACT ____________
    @Override
    public void setTexture(Texture texture) {
        if (this.texture != null) {
            this.texture.dispose();
        }
        this.texture = texture;
        this.playerSprite.setTexture(texture);
    }

    //____________ METHODS ABSTRACT ____________
    @Override
    public void render(SpriteBatch batch) {
        // Dessine le joueur
        playerSprite.draw(batch);

        // Dessine toutes les balles de laine
        for (YarnBall yarnBall : yarnBalls) {
            yarnBall.render(batch);
        }
    }

}


