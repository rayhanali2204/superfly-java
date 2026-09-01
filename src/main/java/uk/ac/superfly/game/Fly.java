package uk.ac.superfly.game;

import java.util.random.RandomGenerator;

public class Fly extends Enemy {
    private static final float MOVEMENT_SCALE = 0.75f;

    Fly(RandomGenerator random, Cake cake, float width, float height) {
        super(random, cake, width, height);
    }

    @Override
    public int scoreValue() {
        return 1;
    }

    @Override
    void move(Cake cake, float width, float height, long tick) {
        double angle = Math.atan2(cake.y() - y(), cake.x() - x());
        float cakePullX = (float) Math.cos(angle) * 0.15f;
        float cakePullY = (float) Math.sin(angle) * 0.15f;
        float wave = (float) Math.sin(tick * 0.1) * 1.5f;

        moveBy((cakePullX + speedX) * MOVEMENT_SCALE, (cakePullY + speedY + wave) * MOVEMENT_SCALE);
        reverseAtBounds(width, height);
    }

    @Override
    Enemy respawn(RandomGenerator random, Cake cake, float width, float height) {
        return new Fly(random, cake, width, height);
    }
}
