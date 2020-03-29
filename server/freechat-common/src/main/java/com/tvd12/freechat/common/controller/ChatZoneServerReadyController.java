package com.tvd12.freechat.common.controller;

import com.tvd12.ezyfoxserver.context.EzyZoneContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractZoneEventController;
import com.tvd12.ezyfoxserver.event.EzyServerReadyEvent;

public class ChatZoneServerReadyController
		extends EzyAbstractZoneEventController<EzyServerReadyEvent> {

	@Override
	public void handle(EzyZoneContext ctx, EzyServerReadyEvent event) {
		logger.info("ChatZoneServerReadyController:: run customer server ready");
	}
	
}
