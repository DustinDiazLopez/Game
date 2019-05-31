package main;

import java.awt.*;

public class Trail extends GameObject {

    private float alpha = 1;
    private float life;

    private Handler handler;

    private Color color;

    private float width;
    private float height;

    public Trail(float x, float y, ID id, Color color, float width, float height, float life, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        this.color = color;
        this.width = width;
        this.height = height;
        this.life = life;
    }

    private AlphaComposite makeTransparent(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return AlphaComposite.getInstance(type, alpha);
    }

    @Override
    public void tick() {
        if (alpha > life) {
            alpha -= (life - 0.001f);
        } else  {
            handler.removeObject(this);
        }
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setComposite(makeTransparent(alpha));

        graphics.setColor(color);
        graphics.fillRect((int) x, (int) y, (int) width, (int) height);

        graphics2D.setComposite(makeTransparent(1));
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
