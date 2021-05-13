//
//  SocketRequests.swift
//  freechat-swift
//
//  Created by Dzung on 10/05/2021.
//  Copyright © 2021 Young Monkeys. All rights reserved.
//

public class SocketRequests {
    private init() {}
    
    public static func sendGetContacts() {
        let requestData = NSMutableDictionary()
        requestData["skip"] = 0
        requestData["limit"] = 100
        getApp()?.send(cmd: Commands.GET_CONTACTS, data: requestData)
    }
    
    public static func sendGetSuggestContacts() {
        getApp()?.send(cmd: Commands.SUGGEST_CONTACTS)
    }
    
    public static func sendBotMessage(message: String) {
        let requestData = NSMutableDictionary()
        requestData["message"] = message
        getApp()?.send(cmd: Commands.CHAT_BOT_MESSAGE, data: requestData)
    }
    
    public static func sendUserMessage(channelId: Int64, message: String) {
        let requestData = NSMutableDictionary()
        requestData["channelId"] = channelId
        requestData["message"] = message
        getApp()?.send(cmd: Commands.CHAT_USER_MESSAGE, data: requestData)
    }
    
    public static func sendSearchContacts(keyword: String) {
        let requestData = NSMutableDictionary()
        requestData["keyword"] = keyword
        getApp()?.send(cmd: Commands.SEARCH_CONTACTS, data: requestData)
    }
    
    public static func sendSearchExistedContacts(keyword: String) {
        let requestData = NSMutableDictionary()
        requestData["skip"] = 0
        requestData["limit"] = 100
        requestData["keyword"] = keyword
        getApp()?.send(cmd: Commands.SEARCH_EXISTED_CONTACTS, data: requestData);
    }
    
    public static func sendAddContacts(user: String) {
        let requestData = NSMutableDictionary()
        requestData["target"] = [user]
        getApp()?.send(cmd: Commands.ADD_CONTACTS, data: requestData)
     }
    
    private static func getApp() -> EzyApp? {
        return EzyClients.getInstance()
            .getDefaultClient()
            .getApp()
    }
}
