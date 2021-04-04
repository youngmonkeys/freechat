package com.tvd12.freechat.data

import com.tvd12.ezyfoxserver.client.constant.EzyConstant
import com.tvd12.ezyfoxserver.client.entity.EzyObject
import com.tvd12.freechat.constant.MessageType
import java.util.*

/**
 * Created by tavandung12 on 10/7/18.
 */

class MessageReceived : Message("") {

    var from: String? = null

    companion object {

        fun create(data: EzyObject) : MessageReceived {
            val answer = MessageReceived()
            answer.deserialize(data)
            return answer
        }

    }

    override fun deserialize(data: EzyObject) {
        super.deserialize(data)
        this.from = data.get<String>("from")
        this.sentTime = Date()
    }

    override fun getType(): EzyConstant {
        return MessageType.RECEIVED
    }
}
