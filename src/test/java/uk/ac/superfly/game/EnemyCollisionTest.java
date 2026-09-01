package uk.ac.superfly.game;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnemyCollisionTest {
    @Test
    void detectsEnemyInsideCakeCollisionRadius() {
        Cake cake = new Cake(200, 200, 1_000, 4);
        Enemy enemy = new Fly(new Random(10), cake, 400, 400);

        assertTrue(enemy.crashesInto(cake));
    }

    @Test
    void rejectsEnemyOutsideCakeCollisionRadius() {
        Cake cake = new Cake(200, 200, 2, 4);
        Enemy enemy = new Fly(new Random(10), cake, 400, 400);

        assertFalse(enemy.crashesInto(cake));
    }
}
