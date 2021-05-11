//
//  ViewManager.swift
//  freechat-swift
//
//  Created by Dzung on 10/05/2021.
//  Copyright © 2021 Young Monkeys. All rights reserved.
//

import UIKit

public class ViewManager {
    private static let INSTANCE = ViewManager()
    
    private init() {}
    
    public static func getInstance() -> ViewManager {
        return INSTANCE
    }
    
    public func showContactsView() {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let contactView = storyboard.instantiateViewController(withIdentifier: "contactsView") as! ContactsController
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.window?.rootViewController = contactView
    }
}
