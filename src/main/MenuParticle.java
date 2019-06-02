package main;

import java.awt.*;
import java.util.Random;

public class MenuParticle extends GameObject {
    private float h = 16;
    private float w = 16;

    private Color color;
    private Random r = new Random();

    private Handler handler;

    public MenuParticle(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        int value = 5;

        velX = (r.nextInt(value + value) - value);
        velY = (r.nextInt(value + value) - value);

        if (velY == 0) {
            velY = 1;
        } else if (velX == 0) {
            velX = 1;
        }

        color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
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

        handler.addObject(new Trail( x, y, ID.Trail, color, w, h, 0.01f, handler));
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect((int) x, (int) y, (int) w, (int) h);
    }
}
