package uk.ac.superfly.game;

import java.util.random.RandomGenerator;

public abstract class Enemy {
    private float x;
    private float y;
    protected float speedX;
    protected float speedY;

    protected Enemy(RandomGenerator random, Cake cake, float width, float height) {
        spawnAwayFromCake(random, cake, width, height);
        speedX = randomBetween(random, -1.2f, 1.2f);
        speedY = randomBetween(random, -1.2f, 1.2f);
    }

    public final float x() {
        return x;
    }

    public final float y() {
        return y;
    }

    public abstract int scoreValue();

    abstract void move(Cake cake, float width, float height, long tick);

    abstract Enemy respawn(RandomGenerator random, Cake cake, float width, float height);

    final boolean crashesInto(Cake cake) {
        return distanceTo(cake.x(), cake.y()) < cake.radius() / 2;
    }

    final float distanceTo(float otherX, float otherY) {
        return (float) Math.hypot(x - otherX, y - otherY);
    }

    protected final void moveBy(float deltaX, float deltaY) {
        x += deltaX;
        y += deltaY;
    }

    protected final void reverseAtBounds(float width, float height) {
        if (x < 0 || x > width) {
            speedX *= -1;
        }
        if (y < 0 || y > height) {
            speedY *= -1;
        }
    }

    protected static float randomBetween(RandomGenerator random, float minimum, float maximum) {
        return minimum + random.nextFloat() * (maximum - minimum);
    }

    private void spawnAwayFromCake(RandomGenerator random, Cake cake, float width, float height) {
        do {
            x = random.nextFloat() * width;
            y = random.nextFloat() * height;
        } while (distanceTo(cake.x(), cake.y()) < 60);
    }
}
