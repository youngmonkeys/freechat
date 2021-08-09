package com.tvd12.ezyfoxserver.client.codec;

import java.util.Map;

import static com.tvd12.ezyfoxserver.client.codec.EzyDecodeState.PREPARE_MESSAGE;
import static com.tvd12.ezyfoxserver.client.codec.EzyDecodeState.READ_MESSAGE_CONTENT;
import static com.tvd12.ezyfoxserver.client.codec.EzyDecodeState.READ_MESSAGE_HEADER;
import static com.tvd12.ezyfoxserver.client.codec.EzyDecodeState.READ_MESSAGE_SIZE;

/**
 * Created by tavandung12 on 9/27/18.
 */

public class EzyDefaultDecodeHandlers extends EzyDecodeHandlers {

    protected EzyDefaultDecodeHandlers(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EzyDecodeHandlers.Builder {
        protected int maxSize;
        protected EzyByteBufferMessageReader messageReader = new EzyByteBufferMessageReader();

        public Builder maxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public EzyDefaultDecodeHandlers build() {
            return new EzyDefaultDecodeHandlers(this);
        }

        @Override
        protected void addHandlers(
                Map<EzyIDecodeState, EzyDecodeHandler> answer) {
            EzyDecodeHandler readMessgeHeader = new ReadMessageHeader();
            EzyDecodeHandler prepareMessage = new PrepareMessage();
            EzyDecodeHandler readMessageSize = new ReadMessageSize(maxSize);
            EzyDecodeHandler readMessageContent = new ReadMessageContent();
            answer.put(PREPARE_MESSAGE, newHandler(prepareMessage, readMessgeHeader));
            answer.put(READ_MESSAGE_HEADER, newHandler(readMessgeHeader, readMessageSize));
            answer.put(READ_MESSAGE_SIZE, newHandler(readMessageSize, readMessageContent));
            answer.put(READ_MESSAGE_CONTENT, newHandler(readMessageContent));
        }


        private EzyDecodeHandler newHandler(EzyDecodeHandler handler) {
            return newHandler(handler, null);
        }

        private EzyDecodeHandler newHandler(EzyDecodeHandler handler, EzyDecodeHandler next) {
            return newHandler((AbstractDecodeHandler)handler, next);
        }

        private EzyDecodeHandler newHandler(AbstractDecodeHandler handler, EzyDecodeHandler next) {
            handler.setNextHandler(next);
            handler.setMessageReader(messageReader);
            return handler;
        }
    }

}
