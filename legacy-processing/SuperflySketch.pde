import java.util.ArrayList;

ArrayList<Explosion> boom = new ArrayList<Explosion>();
Enemy[] enemies = new Enemy[6];
Cake cake = new Cake(200, 200, 120);

int highScore = 0;
int currentScore = 0;
boolean gameOver = false;

String scoreFile = "highscore.txt";

PImage flyImg;
PImage superFlyImg;

void setup() {
  size(400, 400);

  flyImg = loadImage("fly.png");
  superFlyImg = loadImage("superfly.png");

  // Load high score
  String[] saved = loadStrings(scoreFile);
  if (saved != null && saved.length > 0) {
    highScore = int(saved[0]);
  }

  // Create enemies
  for (int i = 0; i < enemies.length; i++) {
    enemies[i] = (i == 0) ? new SuperFly() : new Fly();
  }
}

void draw() {
  background(200, 220, 225);

  if (gameOver) {
    gameOverScreen();
    return;
  }

  cake.display();

  // Enemies
  for (int i = 0; i < enemies.length; i++) {
    Enemy e = enemies[i];
    e.move();
    e.display();

    if (e.crash(cake)) {
      cake.takeHit();
      enemies[i] = (e instanceof SuperFly) ? new SuperFly() : new Fly();

      if (cake.lives <= 0) {
        cake.lives = 0;
        gameOver = true;
      }
    }
  }

  // Explosions
  for (int i = boom.size() - 1; i >= 0; i--) {
    boom.get(i).update();
    if (boom.get(i).isDone()) boom.remove(i);
  }

  // High score
  if (currentScore > highScore) {
    highScore = currentScore;
    saveStrings(scoreFile, new String[]{str(highScore)});
  }

  // UI
  textAlign(LEFT, BASELINE);
  fill(0);
  textSize(16);
  text("Score: " + currentScore, 10, 20);
  text("High Score: " + highScore, 10, 40);
}

void mousePressed() {
  if (gameOver) {
    resetGame();
    return;
  }

  for (int i = 0; i < enemies.length; i++) {
    Enemy e = enemies[i];

    if (dist(mouseX, mouseY, e.x, e.y) < 15) {
      boom.add(new Explosion(e.x, e.y));

      currentScore += (e instanceof SuperFly) ? 2 : 1;

      enemies[i] = (e instanceof SuperFly) ? new SuperFly() : new Fly();
    }
  }
}

void gameOverScreen() {
  textAlign(CENTER, CENTER);
  fill(0);
  textSize(32);
  text("GAME OVER!", width / 2, height / 2 - 20);

  textSize(18);
  text("Final Score: " + currentScore, width / 2, height / 2 + 20);
  text("Click to Restart", width / 2, height / 2 + 50);

  textAlign(LEFT, BASELINE);
}

void resetGame() {
  currentScore = 0;
  boom.clear();
  gameOver = false;
  cake.lives = 4;
  setup();
}
