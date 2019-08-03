package vn.team.freechat;

import android.app.Application;

import com.tvd12.ezyfoxserver.client.socket.EzyMainEventsLoop;
import com.tvd12.ezyfoxserver.client.util.EzyLogger;

/**
 * Created by Dung Ta Van on 8/3/19.
 * Copyright © 2019 Young Monkeys. All rights reserved.
 **/
public class ChatApplication extends Application {

    protected final EzyMainEventsLoop mainEventsLoop;

    public ChatApplication() {
        super();
        EzyLogger.setLevel(EzyLogger.LEVEL_DEBUG);
        this.mainEventsLoop = new EzyMainEventsLoop();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mainEventsLoop.start();
    }
}
