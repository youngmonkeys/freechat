package com.tvd12.freechat.test.game;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.Getter;

@EzyObjectBinding(
    read = false,
    accessType = EzyAccessType.NONE)
public class Ship extends AbstractVirtualRoomItem {

    @Getter
    private final double velocity;
    private Vec3 moveValue;

    public Ship() {
        this.width = 1.0D;
        this.height = 2.4D;
        this.velocity = 10.0D;
        this.position = new Vec3(5, 0, 1);
    }

    @Override
    public void update() {
        if (moveValue == null) {
            return;
        }
        Vec3 v = takeMoveValue();
        position.set(v);
        logger.info("ship position now: " + position);
    }

    public void move(Vec3 value) {
        this.moveValue = value;
    }

    private Vec3 takeMoveValue() {
        Vec3 v = moveValue;
        moveValue = null;
        return v;
    }

    public Vec3 getBulletPos() {
        return new Vec3(position.x, position.y, position.z + height / 2);
    }

    @Override
    public void reset() {
        position.x = 5;
        position.y = 0;
        position.z = 1;
    }
}
