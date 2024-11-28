package entities;

import main.FlappyGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected Rectangle2D.Float collisionbox;

    protected int state;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

//    protected void drawHitboxOLD(Graphics g, int xLvlOffset) {
//        // For debugging the hitbox
//        g.setColor(Color.red);
//        g.drawRect((int) hitbox.x - xLvlOffset + 5, (int) hitbox.y + 25, (int) hitbox.width, (int) hitbox.height);
//
//    }

    protected void drawHitbox(Graphics g, int xLvlOffset) {
        // For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

//    protected void initHitboxOld(float x, float y, float width, float height) {
//        hitbox = new Rectangle2D.Float(x, y, width, height);
//    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * FlappyGame.SCALE), (int) (height * FlappyGame.SCALE));
    }


//	protected void updateHitbox() {
//		hitbox.x = (int) x;
//		hitbox.y = (int) y;
//	}

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

}