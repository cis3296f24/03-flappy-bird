package entities;

import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;
import static utils.Constants.FlappyWorldConstants.*;
import static utils.Constants.PlayerConstants.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.FlappyGame;
import utils.LoadSave;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 25;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 1.25000000000000000000f; // Change this for bird speed fast or slow
    private int[][] lvlData;
    private float xDrawOffset = 21 * FlappyGame.SCALE;
    private float yDrawOffset = 4 * FlappyGame.SCALE;

    // Jumping / Gravity
    private float airSpeed = 10f;
    private float gravity = 0.0401111111111111114f * FlappyGame.SCALE; // Change this for gravity
    private float jumpSpeed = -1.725000000000000000f * FlappyGame.SCALE;   // Change this for how high to jump
    private float fallSpeedAfterCollision = 0.5f * FlappyGame.SCALE;
    private boolean inAir = false;

    // Status UI Bird Health bar. We will be using all or nothing type of health.
    // StatusBarUI
    private BufferedImage statusBarImg;

    private int statusBarWidth = (int) (192 * FlappyGame.SCALE);
    private int statusBarHeight = (int) (58 * FlappyGame.SCALE);
    private int statusBarX = (int) (10 * FlappyGame.SCALE);
    private int statusBarY = (int) (10 * FlappyGame.SCALE);

    private int healthBarWidth = (int) (150 * FlappyGame.SCALE);
    private int healthBarHeight = (int) (4 * FlappyGame.SCALE);
    private int healthBarXStart = (int) (34 * FlappyGame.SCALE);
    private int healthBarYStart = (int) (14 * FlappyGame.SCALE);
    private int healthWidth = healthBarWidth;

    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private Playing playing;

    private  int birdScore = 0;
    private  boolean birdEntered = false;
    private  boolean birdExited = true;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        initHitbox(2, 27);
        // this.currentHealth = 35;
        // this.walkSpeed = FlappyGame.SCALE;

        loadBirdAnimations();

        // To center the box over the bird this is the initHitbox stage
        // to get the dimensions correct.
        // Each bird will be different depending on the resolution.
        // We may need to create several variables for different birds, eagle, crow etc.
        // initHitbox(x, y, (0 * FlappyGame.SCALE) + 1, 15 * FlappyGame.SCALE);
        this.currentHealth = 35;
    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    // The logic in this method works as a toggle switch to keep score
    // It avoids duplicating the score if the bird spends too much inside the fly zone.
    public void updateBirdScore(float x, float y, int[][] lvlData) {
        int xIndex = (int) (x / FlappyGame.TILE_SIZE);
        int yIndex = (int) (y / FlappyGame.TILE_SIZE);
        int currentValue = lvlData[yIndex][xIndex];
        if (currentValue == 23) {
            // Bird is on the scoring tile
            if (!birdEntered) {
                birdEntered = true;
                birdExited = false;
                System.out.println("Bird entered the scoring zone");
            }
        } else {
            // Bird is not on the scoring tile
            if (birdEntered && !birdExited) {
                birdExited = true;
                birdEntered = false;
                birdScore++;
                System.out.println("Bird exited the scoring zone. Current score: " + birdScore);
            }
        }
    }

    private void loadBirdAnimations() {

        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        //               BufferedImage[i Row][j columns]
        animations = new BufferedImage[4][11];
        // System.out.println("BufferedImage[] []" + animations.length);
        // System.out.println("BufferedImage[] []" + animations[0].length);
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++) {
                // Original player
                // animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
                // Flappy Bird
                // animations[j][i] = img.getSubimage(i * 158, (j * 0) + 2, 158, 122);
                // Crow Flappy Bird

                animations[j][i] = img.getSubimage(i * 180 + 30, 0, 200, 185);
                //  System.out.println("animations " + animations[j].length);

            }
        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void update() {
        updateHealthBar();
        updatePos();
        if (currentHealth <= 0) {
            if (state != DEAD) {
                state = DEAD;
                aniTick = 0;
                aniIndex = 0;
                playing.setPlayerDying(true);
            } else if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) {
                playing.setGameOver(true);
            }
        }
        updateAnimationTick();
       // updateHitbox();
    }

    private void updateHealthBar() {
        healthBarWidth = (int)((currentHealth /  maxHealth) * healthBarWidth);
    }

    public void setUpdateHealthBar(int birdHealth) {
        currentHealth = birdHealth;
        //currentHealth = maxHealth; // Resetting this for testing.
    }

    // Added second parameter for scrolling. - Shafiq
    // This is where we can change the aspect ration of the animated object.
    // Here is the original code
    //         g.drawImage(idleAni[aniIndex], (int) xDelta, (int) yDelta, 70, 60, null);
    public void render(Graphics g, int lvlOffset) {
        int scaleBirdX = 50; // scale the bird in x direct by 50 pixels to correct the aspect ratio. -Shafiq.
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset, (int) (hitbox.y - yDrawOffset), width - scaleBirdX, height, null);

        // drawUI(g);  // Draws the health. We can remove this once the bird dies works.

        drawHitbox(g, lvlOffset);
        updateScore(birdScore, g);

    }

    public void updateScore(int score,Graphics g ) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, 50, 50);
    }

    // This method draws the health bar in the upper left corner.
    // We can discuss if we need to keep this. Since there is also an energy bar.
    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }


    // This method makes sure that the bird animations are cycled completely.
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }

        }

    }

    // Start the bird flying.
    // Removed all other if then statements because bird doesn't jump, move left, or attack etc.
    private void setAnimation() {
        int startAni = playerAction;
    }

    // Animation tick is the index for cycling the ImgArray animations [1, 2, 3, ...]
    // and this is how the bird appears to fly.
    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;

        if (jump)
            jump();
        if (!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if (left)
            xSpeed -= playerSpeed;
        if (right)
            xSpeed += playerSpeed;

        if (!inAir)
            if (!IsEntityOnFloor(hitbox, lvlData))
                inAir = true;

        // Adding this to collide bird into pipe and set health to zero.
        if (!CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
            // System.out.println("Bird touching something");
            setUpdateHealthBar(COLLIDED);  // Collide the bird and end game.
        }

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                // This section is true if the bird collides with an area not allowed.
                setUpdateHealthBar(COLLIDED);  // Collide the bird and end game.

                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else {
            // If the bird hits the floor it will die.
            // System.out.println("Not in air");
            setUpdateHealthBar(COLLIDED);   // Collide the bird and end game.
        }

        updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
        //if (inAir)
           // return; I removed these statements so the bird just falls if not pressing space bar.
        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;

    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            System.out.println("hit x: " + hitbox.x + ", y: " + hitbox.y);
            updateBirdScore(hitbox.x,hitbox.y,lvlData);
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
            // This check only sees the collision into the pipes. Please see above
            // where the bird hits the ceiling an floor to set health to 0;
            setUpdateHealthBar(COLLIDED);  // Setting bird collision here since it cannot move here.

        }
    }


    public void changeHealth(int value) {
        currentHealth += value;

        if (currentHealth <= 0)
            currentHealth = 0;
        else if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }

    // We will not be using this feature, If someone would like the bird to gain health eating pills,
    // this can be used to make the bird invincible for x hits before it dies.
    // for example ghost pills that allow the bird to fly through the pipes but keeping score will need
    // also work. -Shafiq
    public void changePower(int value) {
        System.out.println("Added power!");
    }



    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = false;  // This pauses the bird in the air at start of the game.
        System.out.println("Loading lvl data in player.java");

    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = true; // I changed this to true on 11/24/2024 because bird should be in the air.
        attacking = false;
        moving = false;
        playerAction = IDLE;

        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }

}