package main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -1442798787354930462L;
    public static final int WIDTH = 1080 , HEIGHT = WIDTH / 12 * 9;
    private boolean running = false;
    private int FPS;

    private Thread thread;
    private Random random;

    private Handler handler;
    private HUD hud;
    private Spawner spawner;
    private Menu menu;

    public enum STATE {
        MENU,
        GAME,
        HELP,
        QUIT,
        END
    }

    static STATE gameState = STATE.MENU;

    public Game() {
        handler = new Handler();
        hud = new HUD();
        spawner = new Spawner(handler, hud);
        menu = new Menu(this, handler, spawner, hud);
        random = new Random();

        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(menu);

        new Window(WIDTH, HEIGHT, "Ze game", this);

        if (gameState == STATE.GAME) {
            spawner.spawn(ID.BasicEnemy);
        } else {
            for (int i = 0; i < 20; i++) {
                spawner.spawn(ID.MenuParticle);
            }
        }


    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                delta--;
            }

            if (running) {
                render();
            }

            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                FPS = frames;
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();

        if (gameState == STATE.GAME) {
            hud.tick();
            spawner.tick();

            if (HUD.HEALTH <= 0) {
                HUD.HEALTH = 100;
                gameState = STATE.END;
                handler.clearAllEnemy();
            }
        } else if (gameState == STATE.MENU || gameState == STATE.END) {
            menu.tick();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bs.getDrawGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(graphics);

        if (gameState == STATE.GAME) {
            hud.render(graphics);
        } else if (gameState == STATE.MENU || gameState == STATE.HELP || gameState == STATE.END ) {
            menu.render(graphics);
        }

        Font font = new Font("arial", Font.PLAIN, 12);
        graphics.setFont(font);
        graphics.setColor(Color.GREEN);
        graphics.drawString("FPS: " + FPS, WIDTH - 70, HEIGHT - 45);


        graphics.dispose();
        bs.show();
    }

    public static float clamp(float variable, float minimum, float maximum) {
        if (variable >= maximum) {
            return variable = maximum;
        } else if (variable <= minimum) {
            return variable = minimum;
        } else {
            return variable;
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}