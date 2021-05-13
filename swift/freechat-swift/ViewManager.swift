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
        showView(viewId: "contactsNavView")
    }
    
    public func showMessagesView(navigationController: UINavigationController?) {
        let controller = MessagesController()
        controller.title = "Message"
        navigationController?.pushViewController(controller, animated: true)
    }
    
    private func showView(viewId: String) {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let controller = storyboard.instantiateViewController(withIdentifier: viewId)
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.window?.rootViewController = controller
    }
}
