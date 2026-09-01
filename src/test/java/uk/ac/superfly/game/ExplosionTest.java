package uk.ac.superfly.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExplosionTest {
    @Test
    void visualStateProgressesAsLifetimeAdvances() {
        Explosion explosion = new Explosion(10, 20);
        int initialOpacity = explosion.opacity();
        float initialDiameter = explosion.diameter();

        explosion.update();
        explosion.update();

        assertTrue(explosion.opacity() < initialOpacity);
        assertTrue(explosion.diameter() > initialDiameter);
        assertFalse(explosion.isDone());
    }

    @Test
    void completesAfterTwentyUpdates() {
        Explosion explosion = new Explosion(10, 20);

        for (int i = 0; i < 19; i++) {
            explosion.update();
        }
        assertFalse(explosion.isDone());

        explosion.update();

        assertTrue(explosion.isDone());
        assertEquals(10, explosion.x());
        assertEquals(20, explosion.y());
    }
}
