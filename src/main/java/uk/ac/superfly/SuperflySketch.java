package uk.ac.superfly;

import processing.core.PApplet;
import processing.core.PImage;
import uk.ac.superfly.game.Cake;
import uk.ac.superfly.game.Enemy;
import uk.ac.superfly.game.Explosion;
import uk.ac.superfly.game.Game;
import uk.ac.superfly.game.SuperFly;
import uk.ac.superfly.persistence.FileHighScoreRepository;
import uk.ac.superfly.persistence.HighScoreRepository;

import java.nio.file.Path;
import java.util.Random;

public final class SuperflySketch extends PApplet {
    private final HighScoreRepository highScoreRepository =
            new FileHighScoreRepository(Path.of("highscore.txt"));
    private Game game;
    private int highScore;
    private PImage flyImg;
    private PImage superFlyImg;

    public static void main(String[] args) {
        PApplet.main(SuperflySketch.class.getName());
    }

    @Override
    public void settings() {
        size(400, 400);
    }

    @Override
    public void setup() {
        flyImg = loadImage("fly.png");
        superFlyImg = loadImage("superfly.png");

        highScore = highScoreRepository.load();

        game = new Game(width, height, new Random());
    }

    @Override
    public void draw() {
        background(200, 220, 225);

        if (game.isGameOver()) {
            renderGameOver();
            return;
        }

        game.update(frameCount);

        renderCake(game.cake());
        for (Enemy enemy : game.enemies()) {
            renderEnemy(enemy);
        }
        for (Explosion explosion : game.explosions()) {
            renderExplosion(explosion);
        }

        if (game.currentScore() > highScore) {
            highScore = game.currentScore();
            highScoreRepository.save(highScore);
        }

        renderScores();
    }

    @Override
    public void mousePressed() {
        if (game.isGameOver()) {
            game.reset();
            return;
        }

        game.handleClick(mouseX, mouseY);
    }

    private void renderCake(Cake cake) {
        fill(255, 200, 200);
        stroke(150);
        ellipse(cake.x(), cake.y(), cake.radius(), cake.radius());

        fill(0);
        text("Cake Lives: " + cake.lives(), 10, 60);
    }

    private void renderEnemy(Enemy enemy) {
        imageMode(CENTER);
        if (enemy instanceof SuperFly) {
            image(superFlyImg, enemy.x(), enemy.y(), 28, 28);
        } else {
            image(flyImg, enemy.x(), enemy.y(), 20, 20);
        }
    }

    private void renderExplosion(Explosion explosion) {
        fill(255, 150, 0, explosion.opacity());
        noStroke();
        ellipse(explosion.x(), explosion.y(), explosion.diameter(), explosion.diameter());
    }

    private void renderScores() {
        textAlign(LEFT, BASELINE);
        fill(0);
        textSize(16);
        text("Score: " + game.currentScore(), 10, 20);
        text("High Score: " + highScore, 10, 40);
    }

    private void renderGameOver() {
        textAlign(CENTER, CENTER);
        fill(0);
        textSize(32);
        text("GAME OVER!", width / 2f, height / 2f - 20);

        textSize(18);
        text("Final Score: " + game.currentScore(), width / 2f, height / 2f + 20);
        text("Click to Restart", width / 2f, height / 2f + 50);

        textAlign(LEFT, BASELINE);
    }
}
