package io.github.kittykaboom.Players;

import com.badlogic.gdx.graphics.Texture;

public class CatPlayer extends Player {

    public CatPlayer(float x, float y) {
        super(x, y);
        this.texture = new Texture("textures/cat.png");
    }

    @Override
    public void setTexture(Texture texture) {
        if (this.texture != null) {
            this.texture.dispose();
        }
        this.texture = texture;
    }
}

