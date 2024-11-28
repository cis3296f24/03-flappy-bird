package gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

// import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.FlappyGame;
import org.lwjgl.util.zstd.ZSTDOutBuffer;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;

import utils.LoadSave;

import static utils.Constants.FlappyWorldConstants.*;
import static utils.Constants.PlayerConstants.COLLIDED;
import static utils.HelpMethods.GetLevelData;
import static utils.LoadSave.GetSpriteAtlas;
import static utils.LoadSave.LEVEL_ONE_DATA;

public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;

    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;     // Added for the bird when it collides.
    private boolean paused = false;

//    private int xLvlOffset;
//    // private int leftBorder = (int) (0.2 * FlappyGame.GAME_WIDTH) / 2;
//    // private int rightBorder = (int) (0.8 * FlappyGame.GAME_WIDTH) / 2;
//    private int leftBorder = (int) (0.2 * FlappyGame.GAME_WIDTH);
//    private int rightBorder = (int) (0.8 * FlappyGame.GAME_WIDTH);
//    private int maxLvlOffsetX;

    // Added this to get the width of the map.
    // BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);  // For single map game
    BufferedImage[] img = LoadSave.GetAllLevels(); // For multi level game

    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * FlappyGame.GAME_WIDTH) / 2;
    private int rightBorder = (int) (0.8 * FlappyGame.GAME_WIDTH) / 2;
    // private int lvlTilesWide = GetLevelData(img)[0].length;
    private int lvlTilesWide = img[0].getNumXTiles(); // Multi level game. Load the length of the map.
    private int maxTilesOffset = lvlTilesWide - FlappyGame.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * FlappyGame.TILE_SIZE;
    private LevelCompletedOverlay levelCompletedOverlay;

//    private int lvlTilesWide = GetLevelData()[0].length;
//    private int maxTilesOffset = lvlTilesWide - FlappyGame.TILES_IN_WIDTH;
//    private int maxLvlOffsetX = maxTilesOffset * FlappyGame.TILE_SIZE;

    private BufferedImage backgroundImg, flappyGroundImg;
    private BufferedImage flappyBKGLayer1, flappyBKGLayer2, flappyBKGLayer3;
    private int backgroundImgWidth = FlappyGame.GAME_WIDTH;
    private float backgroundImgL1Speed, backgroundImgL2Speed = xLvlOffset;

    // private float backgroundImgSpeed = 0.07f;
    private float backgroundImgSpeed = .07f;
    private float backLayer1Speed = 0.06f;
    private float backLayer2Speed = 0.08f;

    private int backgroundImgCounter = 0;

    // added for collision and bird health etc.
    private boolean gameOver;
    private boolean lvlCompleted;
    private boolean playerDying;

    // int[][] lvlData = GetLevelData(); // Imported this here to add score keeping

    // get the background.
    public Playing(FlappyGame flappyGame) {
        super(flappyGame);
        initClasses();
        backgroundImg = GetSpriteAtlas(LoadSave.FlappyCity_BG_IMG);    // This line loads the flappy background.
        flappyGroundImg = GetSpriteAtlas(LoadSave.GROUND_IMG);         // This will create the floor or flappy ground.
        flappyBKGLayer1 = GetSpriteAtlas(LoadSave.FlappyLayer_1);
        flappyBKGLayer2 = GetSpriteAtlas(LoadSave.FlappyLayer_2);
        flappyBKGLayer3 = GetSpriteAtlas(LoadSave.FlappyLayer_3);
        System.out.println("lvlTilesWide " + lvlTilesWide);
    }

    // To change the initial location of the bird change new Player (x, y ..............
    private void initClasses() {
        levelManager = new LevelManager(flappyGame);

        player = new Player(25, (int)(FlappyGame.GAME_HEIGHT / 2), (int) (64 * FlappyGame.SCALE), (int) (40 * FlappyGame.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);    // Added for when the bird dies after collision.
        levelCompletedOverlay = new LevelCompletedOverlay(this);

    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    private void loadStartLevel() {
        // enemyManager.loadEnemies(levelManager.getCurrentLevel());
        // objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    public void loadNextLevel() {
        System.out.println("Loading next level from Player.java ...");
        resetAll();
        levelManager.loadNextLevel();
        System.out.println("levelManager called .");
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    @Override
    public void update() {
        if (!paused && !gameOver) {
            levelManager.update();
            player.update();
            checkCloseToBorder();
        } else {
            pauseOverlay.update();
        }
    }

//    private void checkCloseToBorder() {
//
//        // System.out.println("xLvlOffset: " + xLvlOffset);
//
//        int playerX = (int) player.getHitbox().x;
//        int diff = playerX - xLvlOffset;
//
//        System.out.println("xLvlOffset: " + xLvlOffset + " playerX +  diff: " + diff);
//
//        if (diff > rightBorder)
//            xLvlOffset += diff - rightBorder;
//        else if (diff < leftBorder)
//            xLvlOffset += diff - leftBorder; // <--------------------------------- center bird
//
//        if (xLvlOffset > maxLvlOffsetX)
//            xLvlOffset = maxLvlOffsetX;
//        else if (xLvlOffset < 0)
//            xLvlOffset = 0;
//    }


    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;
        System.out.println("xLvlOffset: " + xLvlOffset + " playerX +  diff: " + diff);
        System.out.println("rightBorder " + rightBorder);
        System.out.println("leftBorder " + leftBorder);
        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }



    // Please see LoadSave.java to input or load your images.
    // Everytime we draw something for Flappy Bird we need to add it here.
    // I added (int) (xLvlOffset * 0.07) to subtract from the background so it appears to be moving.
    @Override
    public void draw(Graphics g) {
//        backgroundImgMoved = -xLvlOffset * backgroundImgSpeed;
//
//        // Calculate the position where the background needs to reset/loop
//        int bgLoopPoint = FlappyGame.GAME_WIDTH;
//
//        // Draw the first instance of the background image
//        g.drawImage(backgroundImg, (int) backgroundImgMoved, 0, FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null);
//
//        // Check if a second instance needs to be drawn for smooth looping
//        if (backgroundImgMoved + bgLoopPoint < bgLoopPoint) {
//            g.drawImage(backgroundImg, (int) backgroundImgMoved + bgLoopPoint, 0, FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null);
//        }

        // backgroundImgCounter += 1;
        // System.out.println("backgroundImgCounter: " + backgroundImgCounter);
        // System.out.println("xLvlOffset: " + xLvlOffset);
        backgroundImgL1Speed = -xLvlOffset * backLayer1Speed;
        backgroundImgL2Speed = -xLvlOffset * backLayer2Speed;
        // System.out.println("xLvlOffset: " + xLvlOffset / 3);
        g.drawImage(flappyBKGLayer1, (int) backgroundImgL1Speed, 0,FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null); // This will load the image with the dimensions of the game.
        g.drawImage(flappyBKGLayer2, (int) backgroundImgL2Speed, 0,FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null); // This will load the image with the dimensions of the game.

        g.drawImage(flappyBKGLayer1, (int) backgroundImgL1Speed + FlappyGame.GAME_WIDTH * 1, 0,FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null); // This will load the image with the dimensions of the game.
        g.drawImage(flappyBKGLayer2, (int) backgroundImgL2Speed + FlappyGame.GAME_WIDTH * 1, 0,FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null); // This will load the image with the dimensions of the game.

        g.drawImage(flappyBKGLayer1, (int) backgroundImgL1Speed + FlappyGame.GAME_WIDTH * 2, 0,FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null); // This will load the image with the dimensions of the game.
        g.drawImage(flappyBKGLayer2, (int) backgroundImgL2Speed + FlappyGame.GAME_WIDTH * 2, 0,FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null); // This will load the image with the dimensions of the game.

        g.drawImage(flappyBKGLayer1, (int) backgroundImgL1Speed + FlappyGame.GAME_WIDTH + FlappyGame.GAME_WIDTH * 2, 0,FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null); // This will load the image with the dimensions of the game.
        g.drawImage(flappyBKGLayer2, (int) backgroundImgL2Speed + FlappyGame.GAME_WIDTH + FlappyGame.GAME_WIDTH * 2, 0,FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null); // This will load the image with the dimensions of the game.

        g.drawImage(flappyBKGLayer1, (int) backgroundImgL1Speed + ( FlappyGame.GAME_WIDTH * 3), 0,FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null); // This will load the image with the dimensions of the game.
        g.drawImage(flappyBKGLayer2, (int) backgroundImgL2Speed + ( FlappyGame.GAME_WIDTH + 3), 0,FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null); // This will load the image with the dimensions of the game.

       // System.out.println("End of game reached if > " + xLvlOffset);

        drawGround(g);
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        if (paused) {
            g.setColor(new Color(154, 15, 15, 255));
            g.fillRect(0, 0, FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        }
    }

    // This method will draw the ground, grass, water etc.
    // I have trouble in this section to append and repeat.
    private void drawGround(Graphics g) {
        // System.out.println("xLvlOffset " + xLvlOffset);
        // for (int i = 0; i < 20; i++)  {
        for (int j = 0; j < 120; j++) {
            // g.drawImage(flappyGroundImg, j * (int) (GROUND_WIDTH / 10) - (int) (xLvlOffset * 0.7), 850, (int) (GROUND_WIDTH / 10), (int) (GROUND_HEIGHT / 10), null);
            g.drawImage(flappyBKGLayer3, j * (int) (GROUND_WIDTH / 10) - (int) (xLvlOffset * 0.2), 840, (int) (GROUND_WIDTH / 10), (int) (GROUND_HEIGHT / 10), null);

        }
        
    }

    // *********************************************************************************
    // Adding ResetAll() and setGameOver for the bird.

    public void resetAll() {
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying = false;
        player.resetAll();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            player.setAttacking(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }
        player.setRight(true); // I added this to set the forward motion of the bird.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }

    }

    public void mouseDragged(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused)
            pauseOverlay.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseReleased(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseMoved(e);

    }

    public LevelManager getLevelManager() {
        return levelManager;
    }


    public void setLevelCompleted(boolean levelCompleted) {
        this.lvlCompleted = levelCompleted;
        if(levelCompleted)
            FlappyGame.getAudioPlayer().lvlCompleted();
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public void unpauseGame() {
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }

    public void setBirdCollision(boolean birdCollision) {
        if (birdCollision) { player.setUpdateHealthBar(COLLIDED); }
    }

}