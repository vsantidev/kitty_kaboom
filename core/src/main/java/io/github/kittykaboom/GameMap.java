package io.github.kittykaboom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.kittykaboom.Players.CatPlayer;
import io.github.kittykaboom.Players.Player;
import io.github.kittykaboom.Walls.SolidWall;
import io.github.kittykaboom.Walls.Wall;

public class GameMap {
    // private List<String> mapLines = new ArrayList<>();
    private List<Wall> walls = new ArrayList<>();
    private Player player;

    private static final int CELL_WIDTH = 50; // Largeur de cellule en pixels
    private static final int CELL_HEIGHT = 46; // Hauteur de cellule en pixels
    private static final int TOTAL_ROWS = 13; // Nombre total de lignes dans map.txt


    public GameMap(String mapFilePath) {
        loadMap(mapFilePath);
    }

    

    private void loadMap(String mapFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(mapFilePath))) {
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    char cell = line.charAt(col);

                    // Calcul des coordonées x et y
                    int x = col * CELL_WIDTH;
                    int y = (TOTAL_ROWS - row - 1) * CELL_HEIGHT; // Inverse les lignes pour correspondre à l'affichage graphique

                    switch (cell) {
                        case 'x':
                            walls.add(new SolidWall(x, y));
                            break;
                        case 'p':
                            player = new CatPlayer(x, y);
                            break;
                        // Ajoutez d’autres cases pour d’autres éléments
                    }
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<Wall> getWalls() {
        return walls;
    }

    public Player getPlayer() {
        return player;
    }
}
