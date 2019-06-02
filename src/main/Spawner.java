package main;

import java.util.Random;

public class Spawner {
    private Handler handler;
    private HUD hud;

    private int scoreKeep = 0;

    private Random r = new Random();

    private boolean activeIsBoss = false;

    public void spawn(ID id) {
        int spawnX = r.nextInt(Game.WIDTH - 50);
        int spawnY = r.nextInt(Game.HEIGHT - 50);

        if (id == ID.Player) {
            handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler));
        }

        if (id == ID.BasicEnemy) {
            handler.addObject(new BasicEnemy(spawnX, spawnY, ID.BasicEnemy, handler));
        }

        if (id == ID.FastEnemy) {
            handler.addObject(new FastEnemy(spawnX, spawnY, ID.FastEnemy, handler));
        }

        if (id == ID.MenuParticle) {
            handler.addObject(new MenuParticle(spawnX + 50, spawnY + 50, ID.MenuParticle, handler));
        }

        if (id == ID.SmartEnemy) {
            handler.addObject(new SmartEnemy(spawnX, spawnY, ID.SmartEnemy, handler));
        }

        if (id == ID.EnemyBoss) {
            handler.addObject(new EnemyBoss((Game.WIDTH / 2) - 48, -120, ID.EnemyBoss, handler));
        }
    }

    public Spawner(Handler handler, HUD hud) {
        this.handler = handler;
        this.hud = hud;
    }

    public void tick() {
        scoreKeep++;

        if (scoreKeep >= 100) {
            scoreKeep = 0;
            hud.setLevel(hud.getLevel() + 1);

            if (!activeIsBoss) {
                if (hud.getLevel() % 2 == 0) {
                    spawn(ID.BasicEnemy);
                }

                if ((hud.getLevel() % 3 == 0)) {
                    spawn(ID.FastEnemy);
                }

                if (hud.getLevel() % 5 == 0) {
                    spawn(ID.SmartEnemy);
                }
            }

            if (hud.getLevel() == 15) {
                activeIsBoss = false;
                handler.clearAllEnemy();
            }


            if (hud.getLevel() == 10) {
                activeIsBoss = true;
                handler.clearAllEnemy();
                spawn(ID.EnemyBoss);
            }
        }
    }
}
