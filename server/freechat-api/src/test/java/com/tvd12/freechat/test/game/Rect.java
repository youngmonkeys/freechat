package com.tvd12.freechat.test.game;

public class Rect {

    public double x;
    public double z;
    public double width;
    public double height;

    public Rect() {}

    public Rect(double x, double z, double width, double height) {
        this.x = x;
        this.z = z;
        this.width = width;
        this.height = height;
    }

    public double getTopZ() {
        return z + height;
    }

    public double getRightX() {
        return x + width;
    }
}
