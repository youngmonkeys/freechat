package com.tvd12.freechat.test.game;

import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyResettable;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
public abstract class AbstractVirtualRoomItem
    extends EzyLoggable
    implements VirtualRoomItem, EzyResettable {

    private static final AtomicLong ID_GENTER = new AtomicLong();
    @EzyValue
    protected Object id = ID_GENTER.incrementAndGet();
    @EzyValue
    protected double width;
    @EzyValue
    protected double height;
    @EzyValue
    protected Vec3 position;
    @EzyValue
    protected boolean valid = true;

    @Override
    public void destroy() {
        this.valid = false;
    }

    @Override
    public void reset() {
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("(")
            .append("id: ").append(id).append(", ")
            .append("position: ").append(position).append(", ")
            .append("valid: ").append(valid)
            .append(")")
            .toString();
    }

}
