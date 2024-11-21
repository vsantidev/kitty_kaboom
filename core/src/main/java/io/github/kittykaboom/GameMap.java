package io.github.kittykaboom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;

import io.github.kittykaboom.Items.Special.Mouse;
import io.github.kittykaboom.Items.Special.YarnBallPower;
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
    private List<YarnBallPower> yarnBallPower = new ArrayList<>();
    private List<Mouse> mice = new ArrayList<>();
    private List<CatPlayer> players = new ArrayList<>();
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

    public Player getPlayer() {
        return player;
    }

    public List<CatPlayer> getPlayers() {
        return players;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<YarnBallUp> getYarnBallUps() {
        return yarnBallUp;
    }

    public List<YarnBallPower> getYarnBallPowers() {
        return yarnBallPower;
    }

    public List<Mouse> getMice() {
        return mice;
    }


    public boolean isSolidWall(int cellX, int cellY, boolean  destroyIfDestructible) {
        // System.out.println("cell X:");
        // System.out.println(cellX);
        // System.out.println("cell Y:");
        // System.out.println(cellY);
        // Convertit les indices de cellule en coordonnées en pixels
        int wallX = cellX * CELL_WIDTH;
        int wallY = (TOTAL_ROWS - cellY - 1) * CELL_HEIGHT; // Coordonnées inversées
        // System.out.println("wall y:");
        // System.out.println(wallY);        
        // System.out.println("Explosion:");
        // ___ Nouvelle version ___
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);
            if (wall.getBounds().x == wallX && wall.getBounds().y == wallY) {
                if (wall.isDestructible()) {
                    if (destroyIfDestructible) {
                        System.out.println("SoftWall détruit !");
                        walls.remove(i); // Supprime le mur destructible
                    }
                    return false; // Considére les SoftWalls comme non solides si elles sont détruites
                }
                System.out.println("SolidWall détecté");
                return true;
            }
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
                    case 's':
                        walls.add(new SoftWall(x, y)); // Coordonnées alignées sur la grille
                        System.out.println("SoftWall added at: " + x + ", " + y);
                        break;
                    case 'p':
                        String texturePath = players.isEmpty() ? "textures/cat_one.png" : "textures/cat_two.png";
                        CatPlayer newPlayer = new CatPlayer(x, y, texturePath);
                        players.add(newPlayer);
                        break;
                    case 'u':
                        yarnBallUp.add(new YarnBallUp(x, y));
                        break;
                    case 'f':
                        yarnBallPower.add(new YarnBallPower(x, y));
                        break;
                    case 'm':
                        mice.add(new Mouse(x, y));
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
    

    // public void checkMouseCollisions(CatPlayer player) {
    //     Iterator<Mouse> iterator = mice.iterator(); // Assuming you have a List<Mouse> called `mice`

    //     while (iterator.hasNext()) {
    //         Mouse mouse = iterator.next();

    //         if (mouse.getBounds().overlaps(player.getBounds())) {
    //             mouse.activate(player); // Apply any effects from the mouse
    //             iterator.remove(); // Remove the mouse from the game after collision
    //             System.out.println("Mouse collected! Speed Boost applied.");
    //         }
    //     }
    // }

    public boolean isPlayerHit(List<Rectangle> explosionAreas) {
    /*Rectangle playerBounds = player.getBounds(); // Supposons que votre joueur a une méthode getBounds()

        for (Rectangle area : explosionAreas) {
            if (playerBounds.overlaps(area)) {
                return true; // Le joueur est touché
            }
        }*/

        return false; // Le joueur n'est pas dans la zone d'explosion
    }
}
