package levels;
import main.FlappyGame;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.HelpMethods.GetLevelData;
import static utils.HelpMethods.GetPlayerSpawn;


public class Level {

    private BufferedImage img;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;
    private int[][] lvlData;

    public Level(BufferedImage img) {
        // this.lvlData = lvlData;
        this.img = img;
        createLevelData();
        calcLvlOffsets();
        calcPlayerSpawn();
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - FlappyGame.TILES_IN_WIDTH;
        maxLvlOffsetX = FlappyGame.TILE_SIZE * maxTilesOffset;
        System.out.println("Level.java var lvlTilesWide: " + lvlTilesWide);
        System.out.println("maxTilesOffset: " + maxTilesOffset);
        System.out.println("maxLvlOffsetX: " + maxLvlOffsetX);
    }

    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }
    public int getLvlOffset() {
        return maxLvlOffsetX;
    }
    public int getMaxLvlOffsetX() {
        return maxLvlOffsetX;
    }
    public int getMaxTilesOffset() {
        return maxTilesOffset;
    }
//    public int getLvlOffset() {
//        return maxLvlOffsetX;
//    }
//    public int getMaxLvlOffsetX() {
//        return maxLvlOffsetX;
//    }
//    public int getMaxTilesOffset() {
//        return maxTilesOffset;
//    }

}