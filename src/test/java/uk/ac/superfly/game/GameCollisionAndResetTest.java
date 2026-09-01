package uk.ac.superfly.game;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameCollisionAndResetTest {
    private static final int MAX_UPDATES = 100_000;

    @Test
    void enemyCollisionReducesCakeLives() {
        Game game = new Game(400, 400, new Random(40));

        advanceUntilLivesChange(game, 4);

        assertTrue(game.cake().lives() < 4);
    }

    @Test
    void reachingZeroLivesSetsGameOver() {
        Game game = new Game(400, 400, new Random(40));

        advanceUntilGameOver(game);

        assertEquals(0, game.cake().lives());
        assertTrue(game.isGameOver());
    }

    @Test
    void resetRestoresGameStateAndReinitializesEnemies() {
        Game game = new Game(400, 400, new Random(50));
        Enemy clicked = game.enemies().get(0);
        game.handleClick(clicked.x(), clicked.y());
        var enemiesBeforeReset = game.enemies().toArray(Enemy[]::new);

        game.reset();

        assertEquals(0, game.currentScore());
        assertEquals(4, game.cake().lives());
        assertFalse(game.isGameOver());
        assertTrue(game.explosions().isEmpty());
        assertEquals(6, game.enemies().size());
        assertEquals(1, game.enemies().stream().filter(SuperFly.class::isInstance).count());
        assertEquals(5, game.enemies().stream().filter(enemy -> enemy.getClass() == Fly.class).count());
        for (int i = 0; i < enemiesBeforeReset.length; i++) {
            assertNotSame(enemiesBeforeReset[i], game.enemies().get(i));
        }
    }

    @Test
    void resetClearsGameOverState() {
        Game game = new Game(400, 400, new Random(40));
        advanceUntilGameOver(game);

        game.reset();

        assertFalse(game.isGameOver());
        assertEquals(4, game.cake().lives());
    }

    private static void advanceUntilLivesChange(Game game, int startingLives) {
        for (int tick = 1; tick <= MAX_UPDATES && game.cake().lives() == startingLives; tick++) {
            game.update(tick);
        }
        assertTrue(game.cake().lives() < startingLives, "Expected a collision within update limit");
    }

    private static void advanceUntilGameOver(Game game) {
        for (int tick = 1; tick <= MAX_UPDATES && !game.isGameOver(); tick++) {
            game.update(tick);
        }
        assertTrue(game.isGameOver(), "Expected game over within update limit");
    }
}
