package com.tvd12.freechat.common.controller;

import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.context.EzyServerContext;
import com.tvd12.ezyfoxserver.controller.EzyServerReadyController;
import com.tvd12.ezyfoxserver.event.EzyServerReadyEvent;

public class ChatServerReadyController
    extends EzyLoggable
    implements EzyServerReadyController {

    @Override
    public void handle(EzyServerContext ctx, EzyServerReadyEvent event) {
        logger.info("ChatServerReadyController:: run customer server ready");
    }

}
