package com.tvd12.freechat.test.game;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.Getter;

@Getter
@EzyObjectBinding(
    read = false,
    accessType = EzyAccessType.NONE)
public class Bullet extends AbstractVirtualRoomItem {

    private final int velocity;
    private final double seekPerTime;

    public Bullet(Vec3 pos) {
        this.width = 0.2D;
        this.height = 1.0D;
        this.velocity = 20;
        this.position = pos;
        this.seekPerTime = (velocity / 1000.0D) * GameConstants.WORLD_SLEEP_TIME;
    }

    @Override
    public void update() {
        this.position.z += seekPerTime;
        if (position.z >= 14) {
            destroy();
        }
    }
}
