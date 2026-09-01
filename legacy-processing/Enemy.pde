abstract class Enemy {
  float x, y, speedX, speedY;

  abstract void move();
  abstract void display();

  boolean crash(Cake c) {
    float d = dist(x, y, c.x, c.y);
    return d < c.r / 2;
  }
}
