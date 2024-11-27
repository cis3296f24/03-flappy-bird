package utils;

import java.awt.geom.Rectangle2D;
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

    // Old method not working.
//    public static void updateBirdScoreOld(float x, float y, int[][] lvlData) {
//        int xIndex = (int) (x / FlappyGame.TILE_SIZE);
//        int yIndex = (int) (y / FlappyGame.TILE_SIZE);
//        int value = lvlData[yIndex][xIndex];
//
//        // Check if bird enters the score tile
//        if (value == 23 && !birdEntered) {
//            birdEntered = true;
//            birdExited = false;
//            System.out.println("Bird entered the scoring zone");
//        }
//        // Check if bird exits the score tile by moving to a non score tile
//        if (birdEntered && !birdExited) {
//            if (xIndex > 0 && xIndex < lvlData[0].length - 1) {
//                int nextValue = lvlData[yIndex][xIndex + 1];
//                int previousValue = lvlData[yIndex][xIndex - 1];
//
//                // If the bird moves from the tile 23 to another tile
//                if (nextValue == 11 || previousValue == 11) {
//                    birdExited = true;
//                    birdEntered = false;
//                    birdScore++;
//                    System.out.println("Bird exited the scoring zone. Current score: " + birdScore);
//                }
//            }
//        }
//    }

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

//        int nextValue = lvlData[(int) yIndex][(int) xIndex + 1];
 //       int previousValue = lvlData[(int) yIndex][(int) xIndex - 1];

//        if ((value == 23) & (nextValue == 11) & (!(birdEntered) & (birdExited)))  {
//
//            birdEntered = true;
//
//            System.out.println("Bird entered                  <<<<<<<<<<    ");
//            if ((value == 23 ) & (lvlData[(int) yIndex][(int) xIndex + 1] == 11  )) {
//                birdExited = true; // If the bird reached the end and next tile is 11 before tile 23.
//                // birdEntered = false;
//                System.out.println("Bird is exiting after this ");
//                // birdScore++;
//            }
//        }
//
//        if ((value == 11) & (birdExited))  {
//                birdEntered = false;
//               // System.out.println("Entered second if statement !");
//        }

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
}