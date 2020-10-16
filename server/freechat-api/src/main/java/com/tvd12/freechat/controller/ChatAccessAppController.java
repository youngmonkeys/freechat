package com.tvd12.freechat.controller;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.annotation.EzyServerEventHandler;
import com.tvd12.ezyfoxserver.constant.EzyEventNames;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.event.EzyUserAccessAppEvent;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import lombok.Setter;

@Setter
@EzySingleton
@EzyServerEventHandler(EzyEventNames.USER_ACCESS_APP)
@EzyClientRequestListener(EzyEventNames.USER_ACCESS_APP)
public class ChatAccessAppController
        extends EzyAbstractAppEventController<EzyUserAccessAppEvent> {

    @EzyAutoBind("appResponseFactory")
    protected EzyResponseFactory responseFactory;

    @Override
    public void handle(EzyAppContext ctx, EzyUserAccessAppEvent event) {
        logger.info("chat app: fire user: {} access app", event.getUser());
    }
}
