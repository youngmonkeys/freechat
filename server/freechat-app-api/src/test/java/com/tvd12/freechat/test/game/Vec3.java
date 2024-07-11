package com.tvd12.freechat.test.game;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;

@EzyArrayBinding(
    read = false,
    accessType = EzyAccessType.DECLARED_FIELDS)
public class Vec3 {
    public double x;
    public double y;
    public double z;

    public Vec3() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vec3(Vec3 v) {
        this(v.x, v.y, v.z);
    }

    public Vec3(double[] array) {
        this(array[0], array[1], array[2]);
    }

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void add(Vec3 v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public double distance(Vec3 v) {
        double dx = v.x - x;
        double dy = v.y - y;
        double dz = v.z - z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public void negate() {
        x = -x;
        y = -y;
        z = -z;
    }

    public void set(float xx, float yy, float zz) {
        this.x = xx;
        this.y = yy;
        this.z = zz;
    }

    public void set(double[] array) {
        x = array[0];
        y = array[1];
        z = array[2];
    }

    public void set(Vec3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public void subtract(Vec3 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public void multiply(double value) {
        x *= value;
        y *= value;
        z *= value;
    }

    @Override
    public String toString() {
        return "(" +
            x + ", " +
            y + ", " +
            z +
            ")";
    }
}