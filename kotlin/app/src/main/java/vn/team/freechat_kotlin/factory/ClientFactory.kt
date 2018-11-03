package vn.team.freechat_kotlin.factory

import com.tvd12.ezyfoxserver.client.EzyClient
import com.tvd12.ezyfoxserver.client.EzyClients
import com.tvd12.ezyfoxserver.client.command.EzySetup
import com.tvd12.ezyfoxserver.client.config.EzyClientConfig
import com.tvd12.ezyfoxserver.client.constant.EzyCommand
import com.tvd12.ezyfoxserver.client.entity.EzyApp
import com.tvd12.ezyfoxserver.client.entity.EzyArray
import com.tvd12.ezyfoxserver.client.entity.EzyData
import com.tvd12.ezyfoxserver.client.entity.EzyObject
import com.tvd12.ezyfoxserver.client.event.EzyDisconnectionEvent
import com.tvd12.ezyfoxserver.client.event.EzyEventType
import com.tvd12.ezyfoxserver.client.event.EzyLostPingEvent
import com.tvd12.ezyfoxserver.client.event.EzyTryConnectEvent
import com.tvd12.ezyfoxserver.client.handler.*
import com.tvd12.ezyfoxserver.client.request.EzyAccessAppRequest
import com.tvd12.ezyfoxserver.client.request.EzyRequest
import vn.team.freechat_kotlin.Mvc
import vn.team.freechat_kotlin.constant.Commands
import vn.team.freechat_kotlin.data.MessageReceived

/**
 * Created by tavandung12 on 10/7/18.
 */

class ClientFactory {

    private val mvc : Mvc = Mvc.getInstance()

    private constructor()

    companion object {
        private val INSTANCE = ClientFactory()
        fun getInstance() : ClientFactory = INSTANCE
    }

    inner class ExConnectionSuccessHandler : EzyConnectionSuccessHandler() {
        override fun postHandle() {
            val controller = mvc.getController("connection")
            controller.updateViews("hide-loading", null)
        }
    }

    inner class ExDisconnectionHandler : EzyDisconnectionHandler() {
        override fun preHandle(event: EzyDisconnectionEvent) {
            val controller = mvc.getController("connection")
            controller.updateViews("show-loading", null)
        }
    }

    inner class ExHandshakeHandler : EzyHandshakeHandler {
        private val loginRequest : EzyRequest

        constructor(loginRequest: EzyRequest) {
            this.loginRequest = loginRequest
        }

        override fun getLoginRequest(): EzyRequest {
            return loginRequest
        }
    }

    inner class ExLoginSuccessHandler : EzyLoginSuccessHandler() {
        override fun handleLoginSuccess(responseData: EzyData?) {
            val request = EzyAccessAppRequest("freechat")
            client.send(request)
        }
    }

    inner class ExAccessAppHandler : EzyAccessAppHandler() {
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

    inner class ReceivedMessageHandler : EzyAppDataHandler<EzyObject> {
        override fun handle(app: EzyApp, data: EzyObject) {
            val controller = mvc.getController("message")
            val message = MessageReceived.create(data)
            controller.updateViews("add-message", message)
        }
    }

    inner class ContactsResponseHandler : EzyAppDataHandler<EzyObject> {
        override fun handle(app: EzyApp, data: EzyObject) {
            val controller = mvc.getController("contact")
            val usernames = data.get<EzyArray>("contacts")
            controller.updateViews("add-contacts", usernames)
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

    fun newClient(loginRequest: EzyRequest) : EzyClient {
        val config = EzyClientConfig.builder()
                .zoneName("freechat")
                .build()
        val clients = EzyClients.getInstance()
        val client = clients.defaultClient ?: clients.newDefaultClient(config)
        val setup = client.get(EzySetup::class.java)
        setup.addEventHandler(EzyEventType.CONNECTION_SUCCESS, ExConnectionSuccessHandler())
        setup.addEventHandler(EzyEventType.CONNECTION_FAILURE, EzyConnectionFailureHandler())
        setup.addEventHandler(EzyEventType.DISCONNECTION, ExDisconnectionHandler())
        setup.addDataHandler(EzyCommand.HANDSHAKE, ExHandshakeHandler(loginRequest))
        setup.addDataHandler(EzyCommand.LOGIN, ExLoginSuccessHandler())
        setup.addDataHandler(EzyCommand.APP_ACCESS, ExAccessAppHandler())
        setup.addEventHandler(EzyEventType.LOST_PING, LostPingEventHandler())
        setup.addEventHandler(EzyEventType.TRY_CONNECT, TryConnectEventHandler())

        val appSetup = setup.setupApp("freechat")

        appSetup.addDataHandler(Commands.CHAT_GET_CONTACTS, ContactsResponseHandler())
        appSetup.addDataHandler(Commands.CHAT_SYSTEM_MESSAGE, SystemMessageHandler())
        appSetup.addDataHandler(Commands.CHAT_USER_MESSAGE, ReceivedMessageHandler())
        return client
    }

}
