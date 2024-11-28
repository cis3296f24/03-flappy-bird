package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.FlappyGame;
import utils.LoadSave;

public class LevelManager {

    private FlappyGame flappyGame;
    private BufferedImage[] levelSprite;
    private Level levelOne;
    private int lvlIndex = 0;


    public LevelManager(FlappyGame flappyGame) {
        this.flappyGame = flappyGame;
        importOutsideSprites();
        levelOne = new Level(LoadSave.GetLevelData());
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++)
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
    }

//    public void draw(Graphics g) {
//        for (int j = 0; j < FlappyGame.TILES_IN_HEIGHT; j++)
//            for (int i = 0; i < FlappyGame.TILES_IN_WIDTH; i++) {
//               int index = levelOne.getSpriteIndex(i, j);
//                g.drawImage(levelSprite[index], FlappyGame.TILE_SIZE * i, FlappyGame.TILE_SIZE * j, FlappyGame.TILE_SIZE, FlappyGame.TILE_SIZE, null);
//            }
//    }

    public void draw(Graphics g, int lvlOffset) {
        for (int j = 0; j < FlappyGame.TILES_IN_HEIGHT; j++)
            for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
                int index = levelOne.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], FlappyGame.TILE_SIZE * i - lvlOffset, FlappyGame.TILE_SIZE * j, FlappyGame.TILE_SIZE, FlappyGame.TILE_SIZE, null);
            }
    }

    public void update() {
    }
    public Level getCurrentLevel() {
        return levelOne;
    }

    public int getLevelIndex() {
        return lvlIndex;
    }

}