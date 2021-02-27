package com.mygdx.game.Utils;

public enum Direction {
    UP(0,1, 90.0f), DOWN(0,-1, 270.0f), LEFT(-1,0, 180.0f), RIGHT(1,0, 90.0f);

    private int vx;
    private int vy;
    private float angle;

    public float getAngle() {
        return angle;
    }

    public int getVx() {
        return vx;
    }

    public int getVy() {
        return vy;
    }

    Direction(int vx, int vy, float angle) {
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
    }
}
