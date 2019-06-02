package main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter {

    private int rectW = 300;
    private int rectH = 64;

    private Game game;
    private Handler handler;
    private Spawner spawner;
    private HUD hud;

    Menu(Game game, Handler handler, Spawner spawner, HUD hud) {
        this.game = game;
        this.handler = handler;
        this.spawner = spawner;
        this.hud = hud;
    }

    public void tick() {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mouseOver(mx, my, center(Game.WIDTH, rectW), center(Game.HEIGHT, rectH) - 100, rectW, rectH)) {  //PLAY
            if (Game.gameState == Game.STATE.MENU) {
                handler.clearAllEnemy();
                Game.gameState = Game.STATE.GAME;
                spawner.spawn(ID.Player);
                spawner.spawn(ID.BasicEnemy);
            }
        }

        if (mouseOver(mx, my, center(Game.WIDTH, rectW), center(Game.HEIGHT, rectH), rectW, rectH)) {  //HELP
            if (Game.gameState == Game.STATE.MENU) { //Menu help
                Game.gameState = Game.STATE.HELP;
            }
        }

        if (mouseOver(mx, my, center(Game.WIDTH, rectW), center(Game.HEIGHT, rectH) + 100, rectW, rectH)) {  //QUIT
            if (Game.gameState == Game.STATE.HELP || Game.gameState == Game.STATE.END) {  //Back help button
                Game.gameState = Game.STATE.MENU;
            } else if (Game.gameState == Game.STATE.MENU) {
                Game.gameState = Game.STATE.QUIT;
                System.exit(1);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }

    private boolean mouseOver(int mx, int my,int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            return my > y && my < y + height;
        } else {
            return false;
        }
    }

    private int center(int gameDimension, int objectDimension) {
        return gameDimension/2 - objectDimension/2;
    }

    private void ShowRectangle(int w, int h, Graphics graphics, Color color, int offsetW, int OffsetH) {
        graphics.setColor(color);
        graphics.drawRect(center(Game.WIDTH, w) - offsetW,center(Game.HEIGHT, h) - OffsetH, w, h);
    }

    private void ShowString(String string, int w, int h, Graphics graphics, Color color, int offsetW, int OffsetH) {
        graphics.setColor(color);
        graphics.drawString(string, center(Game.WIDTH, w) - offsetW,center(Game.HEIGHT, h) - OffsetH);
    }

    public void render(Graphics graphics) {
        if (Game.gameState == Game.STATE.MENU) {
            Font font = new Font("arial", Font.BOLD, 50);
            Font font2 = new Font("arial", Font.PLAIN, 45);

            graphics.setFont(font);
            ShowString("Menu", 200, 64, graphics, Color.WHITE, -35, 150);

            graphics.setFont(font2);
            int stringOffsetW = -60;
            int stringOffsetH = 5;

            int stringW = 215;
            int stringH = 160;

            ShowString("Play", stringW, stringH, graphics, Color.WHITE, stringOffsetW, stringOffsetH);
            ShowString("Help", stringW, stringH, graphics, Color.WHITE, stringOffsetW, stringOffsetH - 100);
            ShowString("Quit", stringW, stringH, graphics, Color.RED, stringOffsetW - 2, stringOffsetH - 200);

            ShowRectangle(rectW, rectH, graphics, Color.WHITE, 0, 100);
            ShowRectangle(rectW, rectH, graphics, Color.WHITE, 0, 0);
            ShowRectangle(rectW, rectH, graphics, Color.RED, 0, -100);

        } else if (Game.gameState == Game.STATE.HELP) {
            Font font = new Font("arial", Font.BOLD, 50);
            Font font2 = new Font("arial", Font.PLAIN, 45);
            Font font3 = new Font("arial", Font.PLAIN, 25);

            graphics.setFont(font);
            ShowString("Help Menu", 200, 64, graphics, Color.WHITE, 25, 150);

            graphics.setFont(font3);
            ShowString("Use WASD keys to move the player.", 200, 64, graphics, Color.WHITE, 100, 50);

            graphics.setFont(font2);
            int stringOffsetW = -60;
            int stringOffsetH = 5;

            int stringW = 215;
            int stringH = 160;

            ShowString("Back", stringW, stringH, graphics, Color.WHITE, stringOffsetW, stringOffsetH - 200);

            ShowRectangle(rectW, rectH, graphics, Color.WHITE, 0, -100);

        } else if (Game.gameState == Game.STATE.END) {
            Font font = new Font("arial", Font.BOLD, 50);
            Font font2 = new Font("arial", Font.PLAIN, 45);
            Font font3 = new Font("arial", Font.PLAIN, 25);

            graphics.setFont(font);
            ShowString("GAME OVER", 200, 64, graphics, Color.WHITE, 25, 150);

            graphics.setFont(font3);
            ShowString("Your Score: " + hud.getScore() + "\nYour Level: " + hud.getLevel(), 200, 64, graphics, Color.WHITE, 100, 50);

            graphics.setFont(font2);
            int stringOffsetW = -60;
            int stringOffsetH = 5;

            int stringW = 215;
            int stringH = 160;

            ShowString("Back", stringW, stringH, graphics, Color.WHITE, stringOffsetW, stringOffsetH - 200);

            ShowRectangle(rectW, rectH, graphics, Color.WHITE, 0, -100);
        }
    }
}
