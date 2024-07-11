package com.tvd12.freechat.test.game;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.Getter;

@Getter
@EzyObjectBinding(
    read = false,
    accessType = EzyAccessType.NONE)
public class Hazard extends AbstractVirtualRoomItem {

    private final int velocity;
    private final double seekPerTime;

    public Hazard(Vec3 position, int velocity) {
        this.valid = true;
        this.width = 1.0D;
        this.height = 1.0D;
        this.position = position;
        this.velocity = velocity;
        this.seekPerTime = (velocity / 1000.0D) * GameConstants.WORLD_SLEEP_TIME;
    }

    @Override
    public void update() {
        this.position.z -= seekPerTime;
        if (position.z < 0) {
            destroy();
        }
    }

    @Override
    public void destroy() {
        this.valid = false;
    }
}
