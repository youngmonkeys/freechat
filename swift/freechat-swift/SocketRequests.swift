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
        requestData["skip"] = 0;
        requestData["limit"] = 100;
        getApp()?.sendRequest(cmd: "5", data: requestData)
    }
    
    public static func sendGetSuggestContacts() {
        getApp()?.sendReqest(cmd: Commands.SUGGEST_CONTACTS)
    }
    
    private static func getApp() -> EzyApp? {
        return EzyClients.getInstance()
            .getDefaultClient()
            .getApp()
    }
}
