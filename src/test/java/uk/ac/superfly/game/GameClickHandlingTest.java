package uk.ac.superfly.game;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class GameClickHandlingTest {
    @Test
    void clickingNormalFlyAddsOnePointAndPreservesItsType() {
        Game game = new Game(400, 400, new Random(30));
        int enemyIndex = 1;
        Enemy clicked = game.enemies().get(enemyIndex);
        assertNoOtherEnemyOverlaps(game, enemyIndex);

        game.handleClick(clicked.x(), clicked.y());

        Enemy replacement = game.enemies().get(enemyIndex);
        assertEquals(1, game.currentScore());
        assertEquals(Fly.class, replacement.getClass());
        assertNotSame(clicked, replacement);
        assertEquals(1, game.explosions().size());
    }

    @Test
    void clickingSuperFlyAddsTwoPointsAndPreservesItsType() {
        Game game = new Game(400, 400, new Random(30));
        int enemyIndex = 0;
        Enemy clicked = game.enemies().get(enemyIndex);
        assertNoOtherEnemyOverlaps(game, enemyIndex);

        game.handleClick(clicked.x(), clicked.y());

        Enemy replacement = game.enemies().get(enemyIndex);
        assertEquals(2, game.currentScore());
        assertEquals(SuperFly.class, replacement.getClass());
        assertNotSame(clicked, replacement);
        assertEquals(1, game.explosions().size());
    }

    @Test
    void clickingEmptySpaceDoesNotChangeScoreOrEnemies() {
        Game game = new Game(400, 400, new Random(30));
        var originalEnemies = game.enemies().toArray(Enemy[]::new);

        game.handleClick(-100, -100);

        assertEquals(0, game.currentScore());
        assertEquals(0, game.explosions().size());
        for (int i = 0; i < originalEnemies.length; i++) {
            assertEquals(originalEnemies[i], game.enemies().get(i));
        }
    }

    private static void assertNoOtherEnemyOverlaps(Game game, int selectedIndex) {
        Enemy selected = game.enemies().get(selectedIndex);
        long enemiesAtClick = game.enemies().stream()
                .filter(enemy -> enemy.distanceTo(selected.x(), selected.y()) < 15)
                .count();
        assertEquals(1, enemiesAtClick, "Seed must place only the selected enemy within click range");
    }
}
