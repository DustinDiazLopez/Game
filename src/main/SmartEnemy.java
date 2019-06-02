package main;

import java.awt.*;

public class SmartEnemy extends GameObject {
    private float h = 16;
    private float w = 16;

    private Color color = Color.GREEN;

    private Handler handler;
    private GameObject player;

    public SmartEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        for (GameObject object : handler.object) {
            if (object.getId() == ID.Player) {
                player = object;
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) h, (int) w);
    }

    public void calculatePlayerPosition() {
        float spriteSize = 8;
        float differenceX = x - player.getX() - spriteSize;
        float differenceY = y - player.getY() - spriteSize;

        float distance = (float) Math.hypot((double)(x - player.getX()), (double)(y - player.getY()));

        velX = ((-1/distance) * differenceX);
        velY = ((-1/distance) * differenceY);
    }

    public void tick() {
        x += velX;
        y += velY;

        calculatePlayerPosition();

        if (y <= 0 || y >= Game.HEIGHT - ((h * 2) + 6)) {
            velY *= -1;
        }

        if (x <= 0 || x >= Game.WIDTH - w) {
            velX *= -1;
        }

        handler.addObject(new Trail(x, y, ID.Trail, color, w, h, 0.02f, handler));
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect((int) x, (int) y, (int) w, (int) h);
    }
}
