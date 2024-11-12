import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

// We create a panel inside the frame for the game to display the pictures etc.
// Created by Shafiq 11/10/2024

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private int yDelta,xDelta = 550; // Direction for Flappy bird up and down.
    private BufferedImage img; //, subImg; remarked after testing can be removed later. - Shafiq
    private BufferedImage[] idleAni;

    public GamePanel() {

        mouseInputs = new MouseInputs(this);
        // KeyboardInputs keyboardInputs = new KeyboardInputs(this);
        setPanelSize();
        addKeyListener(new KeyboardInputs(this)); // When you press the space bar key the player goes up or flies.
        addMouseListener(new MouseInputs(this)); // Incase we need to make flappy work with mouse clicks.

        addMouseListener(mouseInputs); // I am adding mouse listener to our game incase it get tiresome for the space bar -Shafiq
        addMouseMotionListener(mouseInputs); // I am adding mouse listener to our game incase it get tiresome for the space bar -Shafiq

        // Following method changes the bird direction up

        // Import image/s
        importImg();
        loadAnimations();
    }

    private void loadAnimations() {
        idleAni = new BufferedImage[8];

        for (int i = 0; i < idleAni.length; i++) {
            idleAni[i] = img.getSubimage(i*37, 0, 37, 37);
        }

    }

    // Auto generated to load a file.
    // todo Read this article to see why the e.printStackTrace is bad
    // https://stackoverflow.com/questions/7469316/why-is-exception-printstacktrace-considered-bad-practice
    // - Shafiq.
    private void importImg() {
        // InputStream is = getClass().getResourceAsStream("/player_sprites.png");
        InputStream is = getClass().getResourceAsStream("/bee_R.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

    }

    public void changeXDelta(int value) {
        this.xDelta += value;

    }

    public void changeYDelta(int value) {
        this.yDelta += value;

    }

    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // g.fillRect(100, yDelta, 50, 50);
        // g.drawImage(img, 0, 0, this); This draws the whole png file
        // In order to pick which one to draw we use subImg to get the pic we need from the graphic.

        // subImg = img.getSubimage(1 * 64, 8 * 40, 64, 40);
        // g.drawImage(subImg, (int) xDelta, (int) yDelta, 128, 80, null);
        g.drawImage(idleAni[2], (int) xDelta, (int) yDelta, 40, 40, null);

    }

}