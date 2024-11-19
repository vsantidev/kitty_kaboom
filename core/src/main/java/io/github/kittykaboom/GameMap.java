package io.github.kittykaboom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.Items.Special.YarnBallUp;
import io.github.kittykaboom.Players.CatPlayer;
import io.github.kittykaboom.Players.Player;
import io.github.kittykaboom.Walls.SoftWall;
import io.github.kittykaboom.Walls.SolidWall;
import io.github.kittykaboom.Walls.Wall;

public class GameMap {
    // private List<String> mapLines = new ArrayList<>();
    private List<Wall> walls = new ArrayList<>();
    private List<YarnBallUp> yarnBallUp = new ArrayList<>();
    private Player player;


    private static final int CELL_WIDTH = 50; // Largeur de cellule en pixels
    private static final int CELL_HEIGHT = 46; // Hauteur de cellule en pixels
    private static final int TOTAL_ROWS = 13; // Nombre total de lignes dans map.txt
    private static final int TOTAL_COLS = 15; // Nombre de colonnes dans map.txt

    // Méthodes getters pour CELL_WIDTH et CELL_HEIGHT
    public static int getCellWidth() {
        return CELL_WIDTH;
    }

    public static int getCellHeight() {
        return CELL_HEIGHT;
    }

    public static int getTotalRows() {
        return TOTAL_ROWS;
    }

    public static int getTotalCols() {
        return TOTAL_COLS;
    }




    public GameMap(String mapFilePath) {
        loadMap(mapFilePath);
    }

    

    public boolean isSolidWall(int cellX, int cellY) {
        // System.out.println("cell X:");
        // System.out.println(cellX);
        // System.out.println("cell Y:");
        // System.out.println(cellY);
        // Convertit les indices de cellule en coordonnées en pixels
         int wallX = cellX * CELL_WIDTH;
      
        // System.out.println("wall X:");
        // System.out.println(wallX);

 
        int wallY = (TOTAL_ROWS - cellY - 1) * CELL_HEIGHT; // Coordonnées inversées
        // System.out.println("wall y:");
        // System.out.println(wallY);
        
        // System.out.println("Explosion:");
        // Parcourt la liste des murs pour vérifier les collisions
        for (Wall wall : walls) {
            if (wall.getBounds().x == wallX && wall.getBounds().y == wallY) {
                // System.out.println("Wall Trouvé");

                if (wall.isDestructible()){
                    System.out.println("petable");
                    walls.remove(wall);
                }
                return true; // Mur trouvé
            }

        
            

            // System.out.println(wall.getBounds().x);
            // System.out.println(wall.getBounds().y);
        }

        return false; // Pas de mur à cet emplacement
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
                        case 's': // Soft wall
                            walls.add(new SoftWall(x, y));
                            break;
                        case 'p':
                            player = new CatPlayer(x, y);
                            break;
                        case 'u':
                            yarnBallUp.add(new YarnBallUp(x, y));
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

    public List<YarnBallUp> getYarnBallUps() {
        return yarnBallUp;
    }


    public boolean isPlayerHit(List<Rectangle> explosionAreas) {
    Rectangle playerBounds = player.getBounds(); // Supposons que votre joueur a une méthode getBounds()

        for (Rectangle area : explosionAreas) {
            if (playerBounds.overlaps(area)) {
                return true; // Le joueur est touché
            }
        }

        return false; // Le joueur n'est pas dans la zone d'explosion
    }

    
}
