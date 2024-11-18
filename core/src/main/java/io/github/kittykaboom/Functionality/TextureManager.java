package io.github.kittykaboom.Functionality;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
    private static HashMap<String, Texture> textures = new HashMap<>();

    // public static Texture getTexture(String path) {
    //     if (!textures.containsKey(path)) {
    //         textures.put(path, new Texture(path));
    //     }
    //     return textures.get(path);
    // }

    public static void disposeAll() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
        textures.clear();
    }

    public static Texture getTexture(String path) {
    if (!textures.containsKey(path)) {
        System.out.println("Loading texture: " + path);
        textures.put(path, new Texture(path));
    } else {
        System.out.println("Reusing texture: " + path);
    }
    return textures.get(path);
}
}
