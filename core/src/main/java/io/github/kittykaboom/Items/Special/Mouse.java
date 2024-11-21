package io.github.kittykaboom.Items.Special;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.Functionality.TextureManager;
import io.github.kittykaboom.GameMap;
import io.github.kittykaboom.Players.CatPlayer;

public class Mouse {
    private Texture texture;
    private Rectangle bounds;
    private float x, y;
    private float speedBoost = 100; // Speed increase for the CatPlayer
    private float duration = 500; // Duration of the speed boost
    private boolean isActive = false;
    private float activeTime = 0;
    private Map<CatPlayer, Float> activePlayers = new HashMap<>(); // Associe chaque joueur au temps écoulé

    //____________ CONSTRUCTOR ____________
    public Mouse(float x, float y) {
        this.texture = TextureManager.getTexture("textures/mouse.png");
        this.bounds = new Rectangle(x, y, GameMap.getCellWidth(), GameMap.getCellHeight());
    }

    //____________ METHODS ____________

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    // public void activate(CatPlayer catPlayer) {
    //     if (!isActive) {
    //         catPlayer.setSpeed(catPlayer.getSpeed() + speedBoost);
    //         isActive = true;
    //     }
    //     // if (!activePlayers.containsKey(catPlayer)) {
    //     //     float oldSpeed = catPlayer.getSpeed();
    //     //     catPlayer.setSpeed(oldSpeed + speedBoost); // Augmente la vitesse
    //     //     activePlayers.put(catPlayer, 0f); // Démarre le suivi de la durée
    //     //     System.out.println("Speed Boost activated! Old Speed: " + oldSpeed + ", New Speed: " + catPlayer.getSpeed());
    //     // }
    // }

    public void activate(CatPlayer catPlayer) {
        if (!activePlayers.containsKey(catPlayer)) {
            catPlayer.setSpeed(catPlayer.getSpeed() + speedBoost);
            activePlayers.put(catPlayer, 0f); // Démarrer le timer pour ce joueur
            System.out.println("Speed Boost activated! Player speed: " + catPlayer.getSpeed());
        }
    }
    


    public boolean affectsPlayer(CatPlayer player) {
        return activePlayers.containsKey(player);
    }
    

    public void update(float delta) {
        Iterator<Map.Entry<CatPlayer, Float>> iterator = activePlayers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<CatPlayer, Float> entry = iterator.next();
            CatPlayer player = entry.getKey();
            float elapsedTime = entry.getValue();
    
            // Ajoute le temps écoulé
            elapsedTime += delta * 1000; // Convertir delta en millisecondes

            if (elapsedTime >= duration) {
                player.setSpeed(player.getSpeed() - speedBoost); // Restaure la vitesse
                iterator.remove();
                System.out.println("Speed Boost expired! Player speed: " + player.getSpeed());
            } else {
                activePlayers.put(player, elapsedTime);
            }
        }
    }
    


    // public void update(float delta, CatPlayer catPlayer) {
    //     if (isActive) {
    //         activeTime += delta;
    //         if (activeTime >= duration) {
    //             catPlayer.setSpeed(catPlayer.getSpeed() - speedBoost);
    //             isActive = false;
    //             activeTime = 0;
    //         }
    //     }
    // }
        // Iterator<Map.Entry<CatPlayer, Float>> iterator = activePlayers.entrySet().iterator();

        // while (iterator.hasNext()) {
        //     Map.Entry<CatPlayer, Float> entry = iterator.next();
        //     CatPlayer player = entry.getKey();
        //     float timeElapsed = entry.getValue();
    
        //     timeElapsed += delta;
        //     if (timeElapsed >= duration) {
        //         float oldSpeed = player.getSpeed();
        //         player.setSpeed(oldSpeed - speedBoost); // Restaure la vitesse normale
        //         iterator.remove();
        //         System.out.println("Speed Boost expired! Player speed: " + player.getSpeed());
        //     } else {
        //         activePlayers.put(player, timeElapsed);
        //     }
 


    public void dispose() {
        texture.dispose();
    }
}
    
