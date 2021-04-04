package vn.team.freechat_kotlin.constant

import com.tvd12.ezyfoxserver.client.constant.EzyConstant

/**
 * Created by tavandung12 on 10/5/18.
 */

enum class MessageType constructor(private val id: Int) : EzyConstant {
    SENDING(1),
    SENT(2),
    RECEIVED(3);

    override fun getId(): Int {
        return id
    }

    override fun getName(): String {
        return toString()
    }
}
