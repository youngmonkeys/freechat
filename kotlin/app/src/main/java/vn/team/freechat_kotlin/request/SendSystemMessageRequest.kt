package vn.team.freechat_kotlin.request

import com.tvd12.ezyfoxserver.client.entity.EzyData
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory
import com.tvd12.ezyfoxserver.client.request.EzyRequest
import vn.team.freechat_kotlin.constant.Commands

/**
 * Created by tavandung12 on 10/5/18.
 */

class SendSystemMessageRequest(
    private val message: String
) : EzyRequest {

    override fun serialize() : EzyData {
        return EzyEntityFactory.newObjectBuilder()
                .append("message", message)
                .build()
    }

    override fun getCommand() : String {
        return Commands.CHAT_SYSTEM_MESSAGE
    }
}
