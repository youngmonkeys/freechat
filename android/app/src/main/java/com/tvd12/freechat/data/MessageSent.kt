package com.tvd12.freechat.data;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant
import com.tvd12.freechat.constant.MessageType

/**
 * Created by tavandung12 on 10/7/18.
 */

class MessageSent(
    message: String
) : Message(message) {

    override  fun getType() : EzyConstant {
        return MessageType.SENT
    }
}