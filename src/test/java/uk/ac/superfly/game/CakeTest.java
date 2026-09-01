package uk.ac.superfly.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CakeTest {
    @Test
    void startsWithFourLives() {
        Cake cake = new Cake(200, 200, 120, 4);

        assertEquals(4, cake.lives());
        assertFalse(cake.isDestroyed());
    }

    @Test
    void losesOneLifeWhenHit() {
        Cake cake = new Cake(200, 200, 120, 4);

        cake.takeHit();

        assertEquals(3, cake.lives());
    }

    @Test
    void livesCannotFallBelowZero() {
        Cake cake = new Cake(200, 200, 120, 4);

        for (int i = 0; i < 10; i++) {
            cake.takeHit();
        }

        assertEquals(0, cake.lives());
    }

    @Test
    void reportsDestructionAtZeroLives() {
        Cake cake = new Cake(200, 200, 120, 4);

        for (int i = 0; i < 4; i++) {
            cake.takeHit();
        }

        assertTrue(cake.isDestroyed());
    }

    @Test
    void resetRestoresInitialLives() {
        Cake cake = new Cake(200, 200, 120, 4);
        cake.takeHit();
        cake.takeHit();

        cake.reset();

        assertEquals(4, cake.lives());
        assertFalse(cake.isDestroyed());
    }
}
