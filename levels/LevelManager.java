package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Gamestate;
import main.FlappyGame;
import utils.HelpMethods;
import utils.LoadSave;

import static utils.LoadSave.GetSpriteAtlas;
import static utils.LoadSave.LEVEL_ONE_DATA;

public class LevelManager {

    private FlappyGame flappyGame;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex = 0;

    private Level levelOne;
    BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);

    public LevelManager(FlappyGame flappyGame) {
        this.flappyGame = flappyGame;
        // levelOne = new Level(img);
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel() {
        lvlIndex++;
        if (lvlIndex >= levels.size()) {
            lvlIndex = 0;
            System.out.println("No more levels! Game Completed!");
            Gamestate.state = Gamestate.MENU;
        }
        Level newLevel = levels.get(lvlIndex);
        // flappyGame.getPlaying().getEnemyManager().loadEnemies(newLevel);
        flappyGame.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        flappyGame.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
        // flappyGame.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevels)
            levels.add(new Level(img));
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
        return levels.get(lvlIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }

    public int getLevelIndex() {
        return lvlIndex;
    }
}