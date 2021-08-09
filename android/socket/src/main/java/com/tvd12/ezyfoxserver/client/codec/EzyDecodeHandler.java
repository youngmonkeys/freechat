package com.tvd12.ezyfoxserver.client.codec;

import java.nio.ByteBuffer;
import java.util.Queue;

public interface EzyDecodeHandler {

	/**
	 * Get next state
	 * 
	 * @return the next state
	 */
	EzyIDecodeState nextState();
	
	/**
	 * Get next handler corresponding the next state
	 * 
	 * @return the next handler
	 */
	EzyDecodeHandler nextHandler();
	
	/**
	 * Handler decoding
	 * 
	 * @param in the input
	 * @param out the output
	 * @return true if decoding is successful or not
	 */
	boolean handle(ByteBuffer in, Queue<EzyMessage> out);
	
}
