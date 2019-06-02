package main;

import java.awt.*;

public class HUD {

    public static float HEALTH = 100;
    private float greenValue = 255;
    private int score = 0;
    private int level = 1;

    public void tick() {
        HEALTH = Game.clamp(HEALTH, 0, 100);
        greenValue = Game.clamp(greenValue, 0, 255);
        greenValue = HEALTH * 2;
        score++;
    }

    public void render(Graphics graphics) {
        int x = 15;
        int y = 15;
        int width = 200;
        int height = 32;

        graphics.setColor(Color.GRAY);
        graphics.fillRect(x, y, width, height);
        graphics.setColor(new Color(75, (int) greenValue, 0));
        graphics.fillRect(x, y, (int) HEALTH * 2, height);
        graphics.setColor(Color.WHITE);
        graphics.drawRect(x, y, width, height);


        graphics.drawString("Score: " + score, x, height * 2);
        graphics.drawString("Level: " + level, x, (height * 2) + 16);

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
