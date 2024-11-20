package io.github.kittykaboom.Players;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
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
    private boolean  isLoser = false;
    private Texture loserTexture;
    private float blinkTimer = 0f;
    private List<YarnBall> yarnBalls;
    private int maxYarnBalls = 1;
    private int maxYarnBallsPower = 1;
    private float speed = 200; 

    //____________ CONSTRUCTOR ____________
    public CatPlayer(float x, float y, String texturePath) {
        super(x, y);
        // this.texture = new Texture("textures/cat.png");
        this.texture = new Texture(texturePath); // Charge la texture spécifiée
        playerSprite = new Sprite(this.texture);
        playerSprite.setPosition(x, y);

        // Réduit la taille du sprite et des limites pour éviter les collisions excessives
        int adjustedWidth = GameMap.getCellWidth() - 7;  // 10 pixels de moins en largeur
        int adjustedHeight = GameMap.getCellHeight() - 7; // 10 pixels de moins en hauteur

        this.playerSprite.setSize(adjustedWidth, adjustedHeight);
        this.bounds = new Rectangle(x, y, adjustedWidth, adjustedHeight);
        
        // Initialise la liste de balle de laine
        this.yarnBalls = new ArrayList<>();
    }

    //____________ GETTERS & SETTERS ____________
    @Override
    public Rectangle getBounds(){
        return  bounds;
    }

    public int getMaxYarnBalls(){
        return maxYarnBalls;
    }

    public int getMaxYarnBallsPower(){
        return maxYarnBallsPower;
    }


    //____________ METHODS ____________  
    @Override 
    public void move(float dx, float dy) {
        float newX = bounds.x + dx;
        float newY = bounds.y + dy;
    
        // Vérifie que le joueur reste dans les limites de la carte
        if (newX >= 0 && newX + bounds.width <= GameMap.getTotalCols() * GameMap.getCellWidth()
            && newY >= 0 && newY + bounds.height <= GameMap.getTotalRows() * GameMap.getCellHeight()) {
            bounds.setPosition(newX, newY);
            position.set(newX, newY);
            playerSprite.setPosition(newX, newY);
        }
    } 
    // public void move(float dx, float dy) {
    //     float deltaX = dx * speed * Gdx.graphics.getDeltaTime();
    //     float deltaY = dy * speed * Gdx.graphics.getDeltaTime();
    //     playerSprite.translate(dx, dy);
    //     bounds.setPosition(playerSprite.getX(), playerSprite.getY()); // Met à jour les limites
    // }


    public boolean placeYarnBall(GameScreen gameScreen) {
        int col = (int) Math.round(bounds.x / GameMap.getCellWidth()) * GameMap.getCellWidth();
        int row = (int) Math.round(bounds.y / GameMap.getCellHeight()) * GameMap.getCellHeight();
        if (yarnBalls.size() < maxYarnBalls) {
            // Passe le joueur actuel au constructeur
            YarnBall yarnBall = new BasicYarnBall(col, row, gameScreen, this);
            yarnBalls.add(yarnBall);
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


    public void increaseMaxYarnBallsPower() {
        maxYarnBallsPower++;
    }

    public float getSpeed() {
        return speed;
    }

    public void setLoser(boolean isLoser) {
        this.isLoser = isLoser;
        if (isLoser) {
            loserTexture = new Texture("textures/loser.png"); // Texture pour le joueur perdant
        }
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

    public void setSpeed(float speed) {
        this.speed = speed;
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

        if (isLoser) {
            blinkTimer += Gdx.graphics.getDeltaTime();
            if ((int) (blinkTimer * 10) % 2 == 0) {
                batch.draw(loserTexture, bounds.x, bounds.y, bounds.width, bounds.height);
            }
        } else {
            playerSprite.draw(batch);
        }
    }

}


