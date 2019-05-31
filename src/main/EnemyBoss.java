package main;

import java.awt.*;
import java.util.Random;

public class EnemyBoss extends GameObject {
    private float h = 96;
    private float w = 96;

    private int timer = 80;
    private int timer2 = 50;

    private float lifeTrail = 0.002f;

    private Color color = Color.GRAY;

    private Random r = new Random();

    private Handler handler;
    private GameObject player;

    public EnemyBoss(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        for (GameObject object : handler.object) {
            if (object.getId() == ID.Player) {
                player = object;
            }
        }

        velX = 0;
        velY = 2;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) h, (int) w);
    }

    private void Bounds(boolean boundY, boolean boundX) {
        if (boundY) {
            if (y <= 0 || y >= Game.HEIGHT - ((h * 2) + 6)) {
                velY *= -1;
            }
        }

        if (boundX) {
            if (x <= 0 || x >= Game.WIDTH - w) {
                velX *= -1;
            }
        }
    }

    private void enableTrail(boolean enable) {
        if (enable) {
            handler.addObject(new Trail(x, y, ID.Trail, color, w, h, lifeTrail, handler));
        }
    }


    public void tick() {
        x += velX;
        y += velY;

        if (timer <= 0) {  //goes forward and stops
            velY = 0;
        } else {
            timer--;
        }

        if (timer <= 0) {  //waits
            timer2--;
        }

        if (timer2 <= 0) { //goes left and right
            if (velX == 0) {
                velX = 2;
            }

            if (velX > 0) {
                velX += 0.005f;
            } else if (velX < 0) {
                velX -= 0.005f;
            }

            velX = Game.clamp(velX, -10, 10);

            int spawn = r.nextInt(10);
            if (spawn == 0) {
                handler.addObject(new EnemyBossBullet(x + 48 , y + 48, ID.BasicEnemy, handler));
            }
        }

        Bounds(false, true);

        enableTrail(false);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect((int) x, (int) y, (int) w, (int) h);
    }
}
