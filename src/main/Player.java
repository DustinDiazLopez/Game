package main;

import java.awt.*;
import java.util.Random;

public class Player extends GameObject {

    private float h = 32;
    private float w = 32;

    Random random = new Random();

    private Handler handler;

    public Player(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y,(int) h, (int) w);
    }

    public void collision() {
        for (GameObject temporaryGameObject : handler.object) {
            if (temporaryGameObject.getId() == ID.BasicEnemy
                    || temporaryGameObject.getId() == ID.FastEnemy
                    || temporaryGameObject.getId() == ID.SmartEnemy) {
                if (getBounds().intersects(temporaryGameObject.getBounds())) {
                    HUD.HEALTH -= 2;
                }
            }
        }
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x, 0, (Game.WIDTH - (w + 6)));
        y = Game.clamp(y, 0, (Game.HEIGHT - ((h * 2) + 3)));

        collision();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect((int) x, (int) y, (int) w, (int) h);
    }
}
