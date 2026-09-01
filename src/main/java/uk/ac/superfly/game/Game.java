package uk.ac.superfly.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.random.RandomGenerator;

public final class Game {
    private static final int ENEMY_COUNT = 6;
    private static final float CLICK_RADIUS = 15;

    private final float width;
    private final float height;
    private final RandomGenerator random;
    private final Cake cake = new Cake(200, 200, 120, 4);
    private final List<Enemy> enemies = new ArrayList<>(ENEMY_COUNT);
    private final List<Enemy> enemiesView = Collections.unmodifiableList(enemies);
    private final List<Explosion> explosions = new ArrayList<>();
    private final List<Explosion> explosionsView = Collections.unmodifiableList(explosions);

    private int currentScore;
    private boolean gameOver;

    public Game(float width, float height, RandomGenerator random) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Game bounds must be positive");
        }
        this.width = width;
        this.height = height;
        this.random = random;
        createEnemies();
    }

    public void update(long tick) {
        explosions.removeIf(Explosion::isDone);

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.move(cake, width, height, tick);

            if (enemy.crashesInto(cake)) {
                cake.takeHit();
                enemies.set(i, enemy.respawn(random, cake, width, height));
                if (cake.isDestroyed()) {
                    gameOver = true;
                }
            }
        }

        explosions.forEach(Explosion::update);
    }

    public void handleClick(float clickX, float clickY) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.distanceTo(clickX, clickY) < CLICK_RADIUS) {
                explosions.add(new Explosion(enemy.x(), enemy.y()));
                currentScore += enemy.scoreValue();
                enemies.set(i, enemy.respawn(random, cake, width, height));
            }
        }
    }

    public void reset() {
        currentScore = 0;
        gameOver = false;
        explosions.clear();
        cake.reset();
        createEnemies();
    }

    public Cake cake() {
        return cake;
    }

    public List<Enemy> enemies() {
        return enemiesView;
    }

    public List<Explosion> explosions() {
        return explosionsView;
    }

    public int currentScore() {
        return currentScore;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void createEnemies() {
        enemies.clear();
        enemies.add(new SuperFly(random, cake, width, height));
        for (int i = 1; i < ENEMY_COUNT; i++) {
            enemies.add(new Fly(random, cake, width, height));
        }
    }
}
