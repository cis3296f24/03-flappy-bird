package entities;

import main.FlappyGame;
import utils.LoadSave;

import static utils.Constants.PlayerConstants.*;

public enum PlayerCharacter {
    // Meaning of the these numbers: These digits are for x by y sprite sheets. The bird sheet is only one row
    // therefore the values for all actions is just for the flying animation for row 0 since we are
    // not using sheets which contain birds in rows and columns For eagle all the values are 0 except after load save.
    PIRATE( 5, 6, 3, 1, 3, 4, 8,
            0, 1, 2, 3, 4, 5, 6,
            LoadSave.PLAYER_PIRATE, 7, 8, 64, 40,20, 27, 21, 4),
    ORC( 6, 8, 8, 8, 6, 4, 4,
            0, 1, 1, 1, 2, 4, 5,
            LoadSave.PLAYER_ORC, 6, 8, 100, 100,13, 15, 44, 42),
    SOLDIER( 6, 8, 8, 8, 6, 4, 4,
            0, 1, 1, 1, 2, 5, 6,
            LoadSave.PLAYER_SOLDIER, 7, 8, 100, 100,12, 18, 44, 39),
    EAGLE(0, 0, 0, 0, 0,
            0,0,0,0, 0, 0, 0,
            0,0,
            LoadSave.PLAYER_EAGLE,11,0,180,0,1,18,0,0);
    // Follow this old code to understand the H, W, x ,y etc.
    //   This loads the EAGLE properly.
    // j was row amount
    // i was column amount
    //   animations[j][i] = img.getSubimage(i * 180 + 30, 0, 200, 185);

    public int spriteA_IDLE, spriteA_RUNNING, spriteA_JUMP, spriteA_FALLING, spriteA_ATTACK, spriteA_HIT, spriteA_DEAD;
    public int rowIDLE, rowRUNNING, rowJUMP, rowFALLING, rowATTACK, rowHIT, rowDEAD;
    public String playerAtlas;
    public int rowAmount, colAmount;
    public int spriteW, spriteH;
    public int hitboxW, hitboxH;
    public int xDrawOffset, yDrawOffset;


    /*
     private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
     */

    PlayerCharacter(int spriteA_IDLE, int spriteA_RUNNING, int spriteA_JUMP, int spriteA_FALLING,
                    int spriteA_ATTACK, int spriteA_HIT, int spriteA_DEAD,
                    int rowIDLE, int rowRUNNING, int rowJUMP, int rowFALLING, int rowATTACK, int rowHIT, int rowDEAD,
                    String playerAtlas, int rowAmount, int colAmount, int spriteW, int spriteH, int hitboxW, int hitboxH,
                    int xDrawOffset, int yDrawOffset) {

        // this.spriteA_FLY = spriteA_FLY;
        this.spriteA_IDLE = spriteA_IDLE;
        this.spriteA_RUNNING = spriteA_RUNNING;
        this.spriteA_JUMP = spriteA_JUMP;
        this.spriteA_FALLING = spriteA_FALLING;
        this.spriteA_ATTACK = spriteA_ATTACK;
        this.spriteA_HIT = spriteA_HIT;
        this.spriteA_DEAD = spriteA_DEAD;

        this.rowIDLE = rowIDLE;
        this.rowRUNNING = rowRUNNING;
        this.rowJUMP = rowJUMP;
        this.rowFALLING = rowFALLING;
        this.rowATTACK = rowATTACK;
        this.rowHIT = rowHIT;
        this.rowDEAD = rowDEAD;

        this.playerAtlas = playerAtlas;
        this.rowAmount = rowAmount;
        this.colAmount = colAmount;
        this.spriteW = spriteW;
        this.spriteH = spriteH;

        this.hitboxW = hitboxW;
        this.hitboxH = hitboxH;

        this.xDrawOffset = (int) (xDrawOffset * FlappyGame.SCALE);
        this.yDrawOffset = (int) (yDrawOffset * FlappyGame.SCALE);
    }

    public int getSpriteAmount(int player_action) {
        return switch (player_action) {
            case IDLE -> spriteA_IDLE;
            case RUNNING -> spriteA_RUNNING;
            case JUMP -> spriteA_JUMP;
            case FALLING -> spriteA_FALLING;
            //case ATTACK -> spriteA_ATTACK;
            case HIT -> spriteA_HIT;
            case DEAD -> spriteA_DEAD;
            default -> 1;
        };
    }

    public int getRowIndex(int player_action) {
        return switch (player_action) {
            case IDLE -> rowIDLE;
            case RUNNING -> rowRUNNING;
            case JUMP -> rowJUMP;
            case FALLING -> rowFALLING;
            //case ATTACK -> rowATTACK;
            case HIT -> rowHIT;
            case DEAD -> rowDEAD;
            default -> 1;
        };
    }

}