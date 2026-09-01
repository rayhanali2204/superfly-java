class Cake {
  float x, y, r;
  int lives = 4;

  Cake(float x_, float y_, float r_) {
    x = x_;
    y = y_;
    r = r_;
  }

  void display() {
    fill(255, 200, 200);
    stroke(150);
    ellipse(x, y, r, r);

    fill(0);
    text("Cake Lives: " + lives, 10, 60);
  }

  void takeHit() {
    lives--;
  }
}
