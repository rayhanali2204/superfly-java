package uk.ac.superfly.game;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnemyScoringTest {
    private final Cake cake = new Cake(200, 200, 120, 4);

    @Test
    void normalFlyIsWorthOnePoint() {
        Enemy fly = new Fly(new Random(20), cake, 400, 400);

        assertEquals(1, fly.scoreValue());
    }

    @Test
    void superFlyIsWorthTwoPoints() {
        Enemy superFly = new SuperFly(new Random(20), cake, 400, 400);

        assertEquals(2, superFly.scoreValue());
    }
}
