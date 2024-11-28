package utils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import main.FlappyGame;

public class HelpMethods {

    boolean birdScored = false;
    private static int birdScore = 0;
    // private static boolean birdEntered = false;
    // private static boolean birdExited = true;


    public static boolean CanMoveHereNew(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    if (!IsSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    if (!IsSolid(x, y + height, lvlData)) {
                        return true;
                    }
        return false;
    }

    public static boolean IsSolid(float x, float y, int[][] lvlData) {
        //System.out.println("lvlData[1][1]: " + lvlData[1][1]);
        //System.out.println("lvlData: " + Arrays.stream(lvlData).allMatch(23));

        int maxWidth = lvlData[0].length * FlappyGame.TILE_SIZE;
        // if (x < 0 || x >= FlappyGame.GAME_WIDTH)
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= FlappyGame.GAME_HEIGHT)
            return true;

        // updateBirdScore2(x, y, lvlData);

        float xIndex = x / FlappyGame.TILE_SIZE;
        float yIndex = y / FlappyGame.TILE_SIZE;
        int value = lvlData[(int) yIndex][(int) xIndex];

        if (value == 23) {
            return false;
        }

        if (value >= 48 || value < 0 || value != 11) {
            return true;
            // return false;
        }
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / FlappyGame.TILE_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * FlappyGame.TILE_SIZE;
            int xOffset = (int) (FlappyGame.TILE_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * FlappyGame.TILE_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / FlappyGame.TILE_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * FlappyGame.TILE_SIZE;
            int yOffset = (int) (FlappyGame.TILE_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else
            // Jumping
            return currentTile * FlappyGame.TILE_SIZE;

    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        // Check the pixel below bottomleft and bottomright
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData)) {
                return false;
                }

        return true;

    }

    public static Point GetPlayerSpawn(BufferedImage img) {
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100)
                    return new Point(i * FlappyGame.TILE_SIZE, j * FlappyGame.TILE_SIZE);
            }
        return new Point(1 * FlappyGame.TILE_SIZE, 1 * FlappyGame.TILE_SIZE);
    }

    // This method loads the level and uses getHeight and getWidth of the small pixel board.
    public static int[][] GetLevelData(BufferedImage img) {
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        return lvlData;
    }

      // This method was inside LoadSave but now is moved to this class.
      // Following the template of Kaarin game to keep consistent and not loose functionality.
      // This method loads the level and uses getHeight and getWidth of the small pixel board.
//    public static int[][] GetLevelData() {
//        totalPipeCount = 0;
//        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
//        int[][] lvlData = new int[img.getHeight()][img.getWidth()];
//        //System.out.println("img height: " + img.getHeight());
//        System.out.println("img width: " + img.getWidth());
//        for (int j = 0; j < img.getHeight(); j++)
//            for (int i = 0; i < img.getWidth(); i++) {
//                Color color = new Color(img.getRGB(i, j));
//                int value = color.getRed();
//                if (value >= 48)
//                    value = 0;
//                lvlData[j][i] = value;
//                if (lvlData[j][i] == 15) {
//                    totalPipeCount++; // Count total # of pipes.
//                    System.out.println("Total Pipes = " + totalPipeCount);
//
//                }
//            }
//        // System.out.println("PIPE # " + totalPipeCount);
//        return lvlData;
//
//    }


}