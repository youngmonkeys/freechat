package com.tvd12.freechat.socket

import com.tvd12.ezyfoxserver.client.EzyClient
import com.tvd12.ezyfoxserver.client.EzyClients
import com.tvd12.ezyfoxserver.client.config.EzyClientConfig
import com.tvd12.ezyfoxserver.client.constant.EzyCommand
import com.tvd12.ezyfoxserver.client.constant.EzyDisconnectReason
import com.tvd12.ezyfoxserver.client.entity.EzyApp
import com.tvd12.ezyfoxserver.client.entity.EzyArray
import com.tvd12.ezyfoxserver.client.entity.EzyData
import com.tvd12.ezyfoxserver.client.entity.EzyObject
import com.tvd12.ezyfoxserver.client.event.EzyDisconnectionEvent
import com.tvd12.ezyfoxserver.client.event.EzyEventType
import com.tvd12.ezyfoxserver.client.event.EzyLostPingEvent
import com.tvd12.ezyfoxserver.client.event.EzyTryConnectEvent
import com.tvd12.ezyfoxserver.client.handler.*
import com.tvd12.ezyfoxserver.client.request.EzyAppAccessRequest
import com.tvd12.ezyfoxserver.client.request.EzyLoginRequest
import com.tvd12.ezyfoxserver.client.request.EzyRequest
import com.tvd12.freechat.Mvc
import com.tvd12.freechat.constant.Commands
import com.tvd12.freechat.data.MessageReceived
import com.tvd12.freechat.manager.StateManager

/**
 * Created by tavandung12 on 10/7/18.
 */

const val ZONE_NAME = "freechat"
const val APP_NAME = "freechat"

class SocketClientProxy private constructor() {

    private val client = newClient()
    private val mvc : Mvc = Mvc.getInstance()

    companion object {
        private val INSTANCE = SocketClientProxy()
        fun getInstance() : SocketClientProxy = INSTANCE
    }

    inner class ExConnectionSuccessHandler : EzyConnectionSuccessHandler() {
        override fun postHandle() {
            val controller = mvc.getController("connection")
            controller.updateViews("hide-loading", null)
        }
    }

    inner class ExDisconnectionHandler : EzyDisconnectionHandler() {
        override fun postHandle(event: EzyDisconnectionEvent) {
            val controller = mvc.getController("connection")
            val disconnectReason = event.reason
            if (disconnectReason == EzyDisconnectReason.CLOSE.id) {
                if (StateManager.getInstance().reconnnect) {
                    client.reconnect()
                    StateManager.getInstance().reconnnect = false
                }
            }
            else if(disconnectReason == EzyDisconnectReason.UNAUTHORIZED.id) {
                controller.updateViews("show-authentication", null)
            }
            else {
                controller.updateViews("show-loading", null)
            }
        }
    }

    inner class ExHandshakeHandler : EzyHandshakeHandler() {

        override fun getLoginRequest(): EzyRequest {
            val connectionData = Mvc.getInstance()
                .getModel()
                .get<Map<String, Any>>("connection")!!
            return EzyLoginRequest(
                ZONE_NAME,
                connectionData["username"] as String,
                connectionData["password"] as String
            )
        }
    }

    inner class ExLoginSuccessHandler : EzyLoginSuccessHandler() {
        override fun handleLoginSuccess(responseData: EzyData?) {
            val request = EzyAppAccessRequest(APP_NAME)
            client.send(request)
        }
    }

    inner class ExAccessAppHandler : EzyAppAccessHandler() {
        override fun postHandle(app: EzyApp, data: EzyArray) {
            val controller = mvc.getController("connection")
            controller.updateViews("show-contacts", null)
        }
    }

    inner class LostPingEventHandler : EzyEventHandler<EzyLostPingEvent> {
        override fun handle(event: EzyLostPingEvent) {
            val controller = mvc.getController("connection")
            controller.updateViews("show-lost-ping", event.count)
        }
    }

    inner class TryConnectEventHandler : EzyEventHandler<EzyTryConnectEvent> {
        override fun handle(event: EzyTryConnectEvent) {
            val controller = mvc.getController("connection")
            controller.updateViews("show-try-connect", event.count)
        }
    }

    inner class SuggestContactsResponseHandler : EzyAppDataHandler<EzyObject> {
        override fun handle(app: EzyApp, data: EzyObject) {
            val contacts = data.get("users", EzyArray::class.java)
            val contactController = mvc.getController("contact")
            contactController.updateViews("search-contacts", contacts)
        }
    }

    inner class SearchContactsResponseHandler : EzyAppDataHandler<EzyObject> {
        override fun handle(app: EzyApp, data: EzyObject) {
            val contacts = data.get("users", EzyArray::class.java)
            val contactController = mvc.getController("contact")
            contactController.updateViews("search-contacts", contacts)
        }
    }

    inner class AddContactsResponseHandler : EzyAppDataHandler<EzyArray> {
        override fun handle(app: EzyApp, data: EzyArray) {
            val contactController = mvc.getController("contact")
            contactController.updateViews("add-contacts", data)
        }
    }

    inner class ContactsResponseHandler : EzyAppDataHandler<EzyArray> {
        override fun handle(app: EzyApp, data: EzyArray) {
            val controller = mvc.getController("contact")
            controller.updateViews("add-contacts", data)
        }
    }

    inner class ReceivedMessageHandler : EzyAppDataHandler<EzyObject> {
        override fun handle(app: EzyApp, data: EzyObject) {
            val controller = mvc.getController("message")
            val message = MessageReceived.create(data)
            controller.updateViews("add-message", message)
        }
    }

    inner class SystemMessageHandler : EzyAppDataHandler<EzyObject> {

        override fun handle(app: EzyApp, data: EzyObject) {
            data.put<String>("from", "System")
            val controller = mvc.getController("message")
            val message = MessageReceived.create(data)
            controller.updateViews("add-message", message)
        }
    }

    private fun newClient() : EzyClient {
        val config = EzyClientConfig.builder()
                .zoneName(ZONE_NAME)
                .build()
        val clients = EzyClients.getInstance()
        val client = clients.defaultClient ?: clients.newDefaultClient(config)
        val setup = client.setup()
        setup.addEventHandler(EzyEventType.CONNECTION_SUCCESS, ExConnectionSuccessHandler())
        setup.addEventHandler(EzyEventType.CONNECTION_FAILURE, EzyConnectionFailureHandler())
        setup.addEventHandler(EzyEventType.DISCONNECTION, ExDisconnectionHandler())
        setup.addDataHandler(EzyCommand.HANDSHAKE, ExHandshakeHandler())
        setup.addDataHandler(EzyCommand.LOGIN, ExLoginSuccessHandler())
        setup.addDataHandler(EzyCommand.APP_ACCESS, ExAccessAppHandler())
        setup.addEventHandler(EzyEventType.LOST_PING, LostPingEventHandler())
        setup.addEventHandler(EzyEventType.TRY_CONNECT, TryConnectEventHandler())

        val appSetup = setup.setupApp(APP_NAME)

        appSetup.addDataHandler(Commands.SUGGEST_CONTACTS, SuggestContactsResponseHandler())
        appSetup.addDataHandler(Commands.SEARCH_CONTACTS, SearchContactsResponseHandler())
        appSetup.addDataHandler(Commands.ADD_CONTACTS, AddContactsResponseHandler())
        appSetup.addDataHandler(Commands.CHAT_GET_CONTACTS, ContactsResponseHandler())
        appSetup.addDataHandler(Commands.CHAT_SYSTEM_MESSAGE, SystemMessageHandler())
        appSetup.addDataHandler(Commands.CHAT_USER_MESSAGE, ReceivedMessageHandler())
        return client
    }

    fun connectToServer() {
        val host = "ws.tvd12.com"
        if (client.isConnected) {
            StateManager.getInstance().reconnnect = true
            client.disconnect()
        }
        else {
            client.connect(host, 3005)
        }
    }

    fun isConnected() = client.isConnected
}

