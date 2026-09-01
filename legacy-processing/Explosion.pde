class Explosion {
  float x, y;
  int life = 20;

  Explosion(float x, float y) {
    this.x = x;
    this.y = y;
  }

  void update() {
    fill(255, 150, 0, life * 12);
    noStroke();
    ellipse(x, y, 35 - life, 35 - life);
    life--;
  }

  boolean isDone() {
    return life <= 0;
  }
}
