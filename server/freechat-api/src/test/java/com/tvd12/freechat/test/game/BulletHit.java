package com.tvd12.freechat.test.game;

import lombok.Getter;

@Getter
public class BulletHit {

    private final Bullet bullet;
    private final Hazard hazard;

    public BulletHit(Bullet bullet, Hazard hazard) {
        super();
        this.bullet = bullet;
        this.hazard = hazard;
    }

    @Override
    public String toString() {
        return "hit between bullet: " + bullet +
            " and hazard: " + hazard;
    }
}
