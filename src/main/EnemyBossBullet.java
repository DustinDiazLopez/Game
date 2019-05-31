package main;

import java.awt.*;
import java.util.Random;

public class EnemyBossBullet extends GameObject {
    private float h = 16;
    private float w = 16;

    private Handler handler;

    private Color color = Color.RED;

    private Random r = new Random();

    public EnemyBossBullet(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        velX = r.nextInt(5 - -5) + - 5;
        velY = 5;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) h, (int) w);
    }

    private void Bounds(boolean boundY, boolean boundX) {
        if (boundY) {
            if (y <= 0 || y >= Game.HEIGHT - ((h * 2) + 6)) {
                handler.removeObject(this);
            }
        }

        if (boundX) {
            if (x <= 0 || x >= Game.WIDTH - w) {
                handler.removeObject(this);
            }
        }
    }

    private void enableTrail(boolean enable) {
        if (enable) {
            handler.addObject(new Trail(x, y, ID.Trail, color, w, h, 0.02f, handler));
        }
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        Bounds(true, true);

        enableTrail(true);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect((int) x, (int) y, (int) w, (int) h);
    }
}
