//
//  SocketProxy.swift
//  freechat-swift
//
//  Created by Dzung on 10/05/2021.
//  Copyright © 2021 Young Monkeys. All rights reserved.
//

import Foundation

public let ZONE_APP_NAME = "freechat"

public class SocketProxy {
    
    private let client: EzyClient
    private static let INSTANCE = SocketProxy()
    
    private init() {
        let config = NSMutableDictionary()
        config["clientName"] = ZONE_APP_NAME;
        config["zoneName"] = ZONE_APP_NAME
        let clients = EzyClients.getInstance()!
        client = clients.newClient(config: config)
        let setup = client.setup!
            .addEventHandler(eventType: EzyEventType.CONNECTION_SUCCESS, handler: EzyConnectionSuccessHandler())
            .addEventHandler(eventType: EzyEventType.CONNECTION_FAILURE, handler: EzyConnectionFailureHandler())
            .addEventHandler(eventType: EzyEventType.DISCONNECTION, handler: ExDisconnectionHandler())
            .addDataHandler(cmd: EzyCommand.LOGIN, handler: ExLoginSuccessHandler())
            .addDataHandler(cmd: EzyCommand.APP_ACCESS, handler: ExAppAccessHandler())
            .addDataHandler(cmd: EzyCommand.HANDSHAKE, handler: ExHandshakeHandler())
        _ = setup.setupApp(appName: ZONE_APP_NAME)
            .addDataHandler(cmd: Commands.GET_CONTACTS, handler: GetContactsResponseHandler())
            .addDataHandler(cmd: Commands.SEARCH_EXISTED_CONTACTS, handler: SearchExistedContactsResponseHandler())
            .addDataHandler(cmd: Commands.SUGGEST_CONTACTS, handler: SuggestContactsResponseHandler())
            .addDataHandler(cmd: Commands.SEARCH_CONTACTS, handler: SuggestContactsResponseHandler())
            .addDataHandler(cmd: Commands.ADD_CONTACTS, handler: AddContactsResponseHandler())
            .addDataHandler(cmd: Commands.CHAT_BOT_MESSAGE, handler: BotMessageHandler())
            .addDataHandler(cmd: Commands.CHAT_USER_MESSAGE, handler: ReceivedMessageHandler())
        Thread.current.name = "main";
        clients.processEvents()
    }
    
    public static func getInstance() -> SocketProxy {
        return INSTANCE
    }
    
    public func connectToServer() {
//        let host = "192.168.1.13"
        let host = "ws.tvd12.com"
        client.connect(host: host, port: 3005)
    }
}

class ExDisconnectionHandler: EzyDisconnectionHandler {
    override func postHandle(event: NSDictionary) {
        // do something here
    }
}

class ExHandshakeHandler : EzyHandshakeHandler {
    override func getLoginRequest() -> NSArray {
        let mvc = Mvc.getInstance();
        let connection = mvc.getModel().get(name: "connection") as! Model
        let array = NSMutableArray()
        array.add(ZONE_APP_NAME)
        array.add(connection.get(name: "username")!)
        array.add(connection.get(name: "password")!)
        return array
    }
};

class ExLoginSuccessHandler : EzyLoginSuccessHandler {
    override func handleLoginSuccess(responseData: NSObject) {
        let array = NSMutableArray()
        array.add(ZONE_APP_NAME)
        array.add(NSDictionary())
        client!.send(cmd: EzyCommand.APP_ACCESS, data: array)
    }
};

class ExAppAccessHandler : EzyAppAccessHandler {
    override func postHandle(app: EzyApp, data: NSObject) -> Void {
        Mvc.getInstance()
            .getController(name: "connection")
            .updateViews(action: "show-contacts")
    }
};

class GetContactsResponseHandler : EzyAbstractAppDataHandler<NSArray> {
    override func process(app: EzyApp, data: NSArray) {
        let mvc = Mvc.getInstance()
        let controller = mvc.getController(name: "contact")
        controller.updateViews(action: "add-contacts", data: data)
    }
};

class SearchExistedContactsResponseHandler : EzyAbstractAppDataHandler<NSArray> {
    override func process(app: EzyApp, data: NSArray) {
        let mvc = Mvc.getInstance()
        let contactController = mvc.getController(name: "contact")
        contactController.updateViews(action: "add-contacts", data: data)
    }
}

class SuggestContactsResponseHandler : EzyAbstractAppDataHandler<NSDictionary> {
    override func process(app: EzyApp, data: NSDictionary) {
        let contacts = data["users"] as! NSArray
        let mvc = Mvc.getInstance()
        let contactController = mvc.getController(name: "search-contact")
        contactController.updateViews(action: "search-contacts", data: contacts)
    }
}

class SearchContactsResponseHandler : EzyAbstractAppDataHandler<NSDictionary> {
    override func process(app: EzyApp, data: NSDictionary) {
        let contacts = data["users"] as! NSArray
        let mvc = Mvc.getInstance()
        let contactController = mvc.getController(name: "search-contact")
        contactController.updateViews(action: "search-contacts", data: contacts)
    }
}

class AddContactsResponseHandler : EzyAbstractAppDataHandler<NSArray> {
    override func process(app: EzyApp, data: NSArray) {
        let mvc = Mvc.getInstance()
        let contactController = mvc.getController(name: "search-contact")
        contactController.updateViews(action: "add-contacts", data: data)
    }
}

class BotMessageHandler : EzyAbstractAppDataHandler<NSDictionary> {
    override func process(app: EzyApp, data: NSDictionary) {
        let dict = NSMutableDictionary()
        dict["from"] = "Bot"
        dict["message"] = data["message"]
        let controller = Mvc.getInstance().getController(name: "message")
        controller.updateViews(action: "add-message", data: dict)
    }
}

class ReceivedMessageHandler : EzyAbstractAppDataHandler<NSDictionary> {
    override func process(app: EzyApp, data: NSDictionary) {
        let controller = Mvc.getInstance().getController(name: "message")
        controller.updateViews(action: "add-message", data: data)
    }
}
