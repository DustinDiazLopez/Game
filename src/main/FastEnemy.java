package main;

import java.awt.*;

public class FastEnemy extends GameObject {
    private float h = 16;
    private float w = 16;

    private Color color = Color.CYAN;

    private Handler handler;

    public FastEnemy(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        velX = 2;
        velY = 9;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) h, (int) w);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Game.HEIGHT - ((h * 2) + 6)) {
            velY *= -1;
        }

        if (x <= 0 || x >= Game.WIDTH - w) {
            velX *= -1;
        }

        handler.addObject(new Trail( x, y, ID.Trail, color, w, h, 0.02f, handler));
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect((int) x, (int) y, (int) w, (int) h);
    }
}
