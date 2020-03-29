package com.tvd12.freechat.controller;

import com.tvd12.ezyfoxserver.context.EzyZoneContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractZoneEventController;
import com.tvd12.ezyfoxserver.event.EzyStreamingEvent;

public class ChatStreamingController 
	extends EzyAbstractZoneEventController<EzyStreamingEvent> {

	@Override
	public void handle(EzyZoneContext ctx, EzyStreamingEvent event) {
		ctx.stream(event.getBytes(), event.getSession());
	}

}
