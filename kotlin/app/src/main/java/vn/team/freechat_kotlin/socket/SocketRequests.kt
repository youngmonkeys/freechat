package vn.team.freechat_kotlin.socket

import com.tvd12.ezyfoxserver.client.EzyClient
import com.tvd12.ezyfoxserver.client.EzyClients
import com.tvd12.ezyfoxserver.client.entity.EzyApp
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory

import java.util.Arrays

import vn.team.freechat_kotlin.constant.Commands
import vn.team.freechat_kotlin.request.GetContactsRequest
import vn.team.freechat_kotlin.request.SendSystemMessageRequest
import vn.team.freechat_kotlin.request.SendUserMessageRequest

class SocketRequests private constructor() {
    companion object {
        fun sendGetContacts() {
            getApp()?.send(GetContactsRequest(0, 50))
        }

        fun sendGetSuggestContacts() {
            getApp()?.send(Commands.SUGGEST_CONTACTS)
        }

        fun sendAddContact(user: String) {
            sendAddContacts(Arrays.asList(user))
        }

        fun sendSearchContacts(keyword: String) {
            val app = getApp()
            if(app != null) {
                val data = EzyEntityFactory.newObjectBuilder()
                    .append("keyword", keyword)
                    .build()
                app.send(Commands.SEARCH_CONTACTS, data)
            }
        }

        fun sendAddContacts(users: Collection<String>) {
            val app = getApp()
            if(app != null) {
                val data = EzyEntityFactory.newObjectBuilder()
                    .append("target", users)
                    .build()
                app.send(Commands.ADD_CONTACTS, data)
            }
        }

        fun sendSystemMessage(message: String) {
            getApp()?.send(SendSystemMessageRequest(message))
        }

        fun sendUserMessage(channelId: Long, message: String) {
            getApp()?.send(SendUserMessageRequest(channelId, message))
        }

        private fun getApp(): EzyApp? {
            val zone = getClient()?.zone ?: return null
            return zone.app
        }

        private fun getClient(): EzyClient? {
            val clients = EzyClients.getInstance()
            return clients.defaultClient
        }
    }
}
