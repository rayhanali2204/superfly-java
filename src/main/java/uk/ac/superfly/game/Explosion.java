package uk.ac.superfly.game;

public final class Explosion {
    private final float x;
    private final float y;
    private int life = 20;
    private int displayLife = 20;

    Explosion(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public int opacity() {
        return displayLife * 12;
    }

    public float diameter() {
        return 35 - displayLife;
    }

    void update() {
        displayLife = life;
        life--;
    }

    boolean isDone() {
        return life <= 0;
    }
}
