package uk.ac.superfly.game;

public final class Cake {
    private final float x;
    private final float y;
    private final float radius;
    private final int initialLives;
    private int lives;

    Cake(float x, float y, float radius, int initialLives) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.initialLives = initialLives;
        this.lives = initialLives;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float radius() {
        return radius;
    }

    public int lives() {
        return lives;
    }

    void takeHit() {
        if (lives > 0) {
            lives--;
        }
    }

    boolean isDestroyed() {
        return lives == 0;
    }

    void reset() {
        lives = initialLives;
    }
}
