package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;
    private boolean keyReleased = false;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
//		case KeyEvent.VK_W:
//			gamePanel.getGame().getPlayer().setUp(false);
//			break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(false);
                break;
//		case KeyEvent.VK_S:
//			gamePanel.getGame().getPlayer().setDown(false);
//			break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                gamePanel.getGame().getPlayer().setJump(false);
                keyReleased = true;
                // System.out.println("Key released");
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
//		case KeyEvent.VK_W:
//			gamePanel.getGame().getPlayer().setUp(true);
//			break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(true);
                break;
//		case KeyEvent.VK_S:
//			gamePanel.getGame().getPlayer().setDown(true);
//			break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                System.out.println(keyReleased);

                // I added the if else so bird falls if space is pressed down.
                if (keyReleased) {
                        gamePanel.getGame().getPlayer().setJump(true);
                    } else gamePanel.getGame().getPlayer().setJump(false);
                // System.out.println("Key pressed");
                keyReleased = false;
                break;
        }
    }
}