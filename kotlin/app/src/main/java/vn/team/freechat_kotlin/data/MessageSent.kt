package vn.team.freechat_kotlin.data;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant
import vn.team.freechat_kotlin.constant.MessageType

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