package vn.team.freechat_kotlin.data;

import com.tvd12.ezyfoxserver.client.constant.EzyConstant
import vn.team.freechat_kotlin.constant.MessageType

/**
 * Created by tavandung12 on 10/7/18.
 */

class MessageSent : Message {

    private var to : String

    constructor(message: String, to: String) : super(message) {
        this.to = to
    }

    fun getTo() : String {
        return to
    }

    override  fun getType() : EzyConstant {
        return MessageType.SENT
    }
}