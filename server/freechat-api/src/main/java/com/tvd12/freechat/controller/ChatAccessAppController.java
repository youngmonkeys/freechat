package com.tvd12.freechat.controller;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.constant.EzyEventNames;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.event.EzyUserAccessAppEvent;

@EzySingleton
@EzyEventHandler(event = EzyEventNames.USER_ACCESS_APP)
public class ChatAccessAppController
    extends EzyAbstractAppEventController<EzyUserAccessAppEvent> {

    @Override
    public void handle(EzyAppContext ctx, EzyUserAccessAppEvent event) {
        logger.info("chat app: fire user: {} access app", event.getUser());
    }
}
