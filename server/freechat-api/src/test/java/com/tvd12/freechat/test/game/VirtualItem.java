package com.tvd12.freechat.test.game;

import com.tvd12.ezyfox.util.EzyDestroyable;

public interface VirtualItem extends EzyDestroyable {

    Object getId();

    void update();

    boolean isValid();

}
