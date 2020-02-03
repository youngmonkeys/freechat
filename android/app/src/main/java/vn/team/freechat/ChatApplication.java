package vn.team.freechat;

import android.app.Application;

import com.tvd12.ezyfoxserver.client.socket.EzyMainEventsLoop;
import com.tvd12.ezyfoxserver.client.logger.EzyLogger;

import vn.team.freechat.mvc.Mvc;

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
        Mvc mvc = Mvc.getInstance();
        mvc.addController("connection");
        mvc.addController("login");
        mvc.addController("contact");
        mvc.addController("message");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mainEventsLoop.start();
    }
}
