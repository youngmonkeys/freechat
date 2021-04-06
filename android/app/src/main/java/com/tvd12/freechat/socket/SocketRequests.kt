package com.tvd12.freechat.socket

import com.tvd12.ezyfoxserver.client.EzyClient
import com.tvd12.ezyfoxserver.client.EzyClients
import com.tvd12.ezyfoxserver.client.entity.EzyApp
import com.tvd12.ezyfoxserver.client.factory.EzyEntityFactory

import com.tvd12.freechat.constant.Commands
import com.tvd12.freechat.request.GetContactsRequest
import com.tvd12.freechat.request.SendSystemMessageRequest
import com.tvd12.freechat.request.SendUserMessageRequest

class SocketRequests private constructor() {
    companion object {
        fun sendGetContacts() {
            getApp()?.send(GetContactsRequest(0, 50))
        }

        fun sendGetSuggestContacts() {
            getApp()?.send(Commands.SUGGEST_CONTACTS)
        }

        fun sendAddContact(user: String) {
            sendAddContacts(listOf(user))
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

        private fun sendAddContacts(users: Collection<String>) {
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
