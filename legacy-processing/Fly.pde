class Fly extends Enemy {

  Fly() {
    do {
      x = random(width);
      y = random(height);
    } while (dist(x, y, cake.x, cake.y) < 60);

    speedX = random(-1.2, 1.2);
    speedY = random(-1.2, 1.2);
  }

  void move() {
    float angle = atan2(cake.y - y, cake.x - x);
    x += cos(angle) * 0.15;
    y += sin(angle) * 0.15;

    x += speedX;
    y += speedY + sin(frameCount * 0.1) * 1.5;

    if (x < 0 || x > width) speedX *= -1;
    if (y < 0 || y > height) speedY *= -1;
  }

  void display() {
    imageMode(CENTER);
    image(flyImg, x, y, 20, 20);
  }
}
