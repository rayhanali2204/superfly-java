class SuperFly extends Fly {

  SuperFly() {
    super();
    speedX = random(-3, 3);
    speedY = random(-3, 3);
  }

  @Override
  void move() {
    x += speedX * 1.5;
    y += speedY * 1.5 + sin(frameCount * 0.3) * 4;

    if (x < 0 || x > width) speedX *= -1;
    if (y < 0 || y > height) speedY *= -1;
  }

  void display() {
    imageMode(CENTER);
    image(superFlyImg, x, y, 28, 28);
  }
}
