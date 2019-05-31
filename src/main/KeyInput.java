package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;

    private boolean[] keyDown = new boolean[4];


    public KeyInput(Handler handler) {
        this.handler = handler;

        for (boolean key : keyDown) {
            key = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (GameObject temporaryObject : handler.object) {
            if (temporaryObject.getId() == ID.Player) {
                if (key == KeyEvent.VK_W) {
                    temporaryObject.setVelY(-5);
                    keyDown[0] = true;
                }
                if (key == KeyEvent.VK_S) {
                    temporaryObject.setVelY(5);
                    keyDown[1] = true;
                }
                if (key == KeyEvent.VK_D) {
                    temporaryObject.setVelX(5);
                    keyDown[2] = true;
                }
                if (key == KeyEvent.VK_A) {
                    temporaryObject.setVelX(-5);
                    keyDown[3] = true;
                }
            }
        }

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (GameObject temporaryObject : handler.object) {
            if (temporaryObject.getId() == ID.Player) {
                if (key == KeyEvent.VK_W) {
                    keyDown[0] = false;
                }
                if (key == KeyEvent.VK_S) {
                    keyDown[1] = false;
                }
                if (key == KeyEvent.VK_D) {
                    keyDown[2] = false;
                }
                if (key == KeyEvent.VK_A) {
                    keyDown[3] = false;
                }

                if (!keyDown[0] && !keyDown[1]) {
                    temporaryObject.setVelY(0);
                }

                if (!keyDown[2] && !keyDown[3]) {
                    temporaryObject.setVelX(0);
                }
            }
        }
    }
}
