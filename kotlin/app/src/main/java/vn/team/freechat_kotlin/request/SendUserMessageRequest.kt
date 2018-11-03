package vn.team.freechat_kotlin.request

import com.tvd12.ezyfoxserver.client.entity.EzyData
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory
import com.tvd12.ezyfoxserver.client.request.EzyRequest
import vn.team.freechat_kotlin.constant.Commands

/**
 * Created by tavandung12 on 10/6/18.
 */

class SendUserMessageRequest : EzyRequest {

    private val to : String
    private val message: String

    constructor(to: String, message: String) {
        this.to = to;
        this.message = message
    }

    override fun serialize() : EzyData {
        return EzyEntityFactory.newObjectBuilder()
                .append("to", to)
                .append("message", message)
                .build()
    }

    override fun getCommand() : String {
        return Commands.CHAT_USER_MESSAGE
    }
}