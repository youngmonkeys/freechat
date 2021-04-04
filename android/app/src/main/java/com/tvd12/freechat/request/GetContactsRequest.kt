package com.tvd12.freechat.request

import com.tvd12.ezyfoxserver.client.entity.EzyData
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory
import com.tvd12.ezyfoxserver.client.request.EzyRequest
import com.tvd12.freechat.constant.Commands

/**
 * Created by tavandung12 on 10/3/18.
 */

class GetContactsRequest(
    private val skip: Int,
    private val limit: Int
) : EzyRequest {

    override fun serialize() : EzyData {
        return EzyEntityFactory.newObjectBuilder()
                .append("skip", skip)
                .append("limit", limit)
                .build()
    }

    override fun getCommand() : String {
        return Commands.CHAT_GET_CONTACTS
    }
}
