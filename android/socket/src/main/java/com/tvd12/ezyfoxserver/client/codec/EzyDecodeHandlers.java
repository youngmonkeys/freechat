package com.tvd12.ezyfoxserver.client.codec;

import com.tvd12.ezyfoxserver.client.builder.EzyBuilder;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public abstract class EzyDecodeHandlers {

	protected EzyIDecodeState state;
	protected Map<EzyIDecodeState, EzyDecodeHandler> handlers;

	protected EzyDecodeHandlers(Builder builder) {
		this.state = firstState();
		this.handlers = builder.newHandlers();
	}
	
	public void handle(ByteBuffer in, Queue<EzyMessage> out) {
		EzyDecodeHandler handler = handlers.get(state);
		while(handler != null && handler.handle(in, out)) {
			state = handler.nextState();
			handler = handler.nextHandler();
		}
	}
	
	protected EzyIDecodeState firstState() {
		return EzyDecodeState.PREPARE_MESSAGE;
	}

	public abstract static class Builder implements EzyBuilder<EzyDecodeHandlers> {
		
		protected Map<EzyIDecodeState, EzyDecodeHandler> newHandlers() {
			Map<EzyIDecodeState, EzyDecodeHandler> answer = new HashMap<>();
			addHandlers(answer);
			return answer;
		}
		
		protected abstract void addHandlers(Map<EzyIDecodeState, EzyDecodeHandler> answer);
	}
	
}
