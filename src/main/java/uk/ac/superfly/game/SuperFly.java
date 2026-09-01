package uk.ac.superfly.game;

import java.util.random.RandomGenerator;

public final class SuperFly extends Fly {
    private static final float MOVEMENT_SCALE = 0.60f;

    SuperFly(RandomGenerator random, Cake cake, float width, float height) {
        super(random, cake, width, height);
        speedX = randomBetween(random, -3, 3);
        speedY = randomBetween(random, -3, 3);
    }

    @Override
    public int scoreValue() {
        return 2;
    }

    @Override
    void move(Cake cake, float width, float height, long tick) {
        float wave = (float) Math.sin(tick * 0.3) * 4;
        moveBy(speedX * 1.5f * MOVEMENT_SCALE, (speedY * 1.5f + wave) * MOVEMENT_SCALE);
        reverseAtBounds(width, height);
    }

    @Override
    Enemy respawn(RandomGenerator random, Cake cake, float width, float height) {
        return new SuperFly(random, cake, width, height);
    }
}
