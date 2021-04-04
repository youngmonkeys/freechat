package com.tvd12.freechat.request

import com.tvd12.ezyfoxserver.client.entity.EzyData
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory
import com.tvd12.ezyfoxserver.client.request.EzyRequest
import com.tvd12.freechat.constant.Commands

/**
 * Created by tavandung12 on 10/6/18.
 */

class SendUserMessageRequest(
    private val channelId: Long,
    private val message: String
) : EzyRequest {

    override fun serialize() : EzyData {
        return EzyEntityFactory.newObjectBuilder()
                .append("channelId", channelId)
                .append("message", message)
                .build()
    }

    override fun getCommand() : String {
        return Commands.CHAT_USER_MESSAGE
    }
}