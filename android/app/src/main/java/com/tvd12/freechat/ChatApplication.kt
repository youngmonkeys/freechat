package com.tvd12.freechat

import android.app.Application
import com.tvd12.ezyfoxserver.client.socket.EzyMainEventsLoop
import com.tvd12.ezyfoxserver.client.logger.EzyLogger

open class ChatApplication : Application() {

    val mainEventsLoop: EzyMainEventsLoop

    init {
        EzyLogger.setLevel(EzyLogger.LEVEL_DEBUG)
        this.mainEventsLoop = EzyMainEventsLoop()
    }

    override fun onCreate() {
        super.onCreate()
        this.mainEventsLoop.start()
    }

}