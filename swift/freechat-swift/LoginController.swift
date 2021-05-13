//
//  ViewController.swift
//  hello-swift
//
//  Created by Dung Ta Van on 10/29/18.
//  Copyright © 2018 Young Monkeys. All rights reserved.
//

import UIKit

class LoginController: UIViewController {
    
    @IBOutlet weak var usernameView: UITextField!
    @IBOutlet weak var passwordView: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let mvc = Mvc.getInstance()
        mvc.getModel().set(name: "connection", value: Model())
        mvc.getModel().set(name: "chat", value: Model())
        mvc.addController(name: "contact", controller: Controller())
        mvc.addController(name: "search-contact", controller: Controller())
        mvc.addController(name: "connection", controller: Controller())
        mvc.addController(name: "message", controller: Controller())
        class ShowContactsView: View {
            func update(component: String, data: Any) {
                ViewManager.getInstance().showContactsView()
            }
        }
        mvc.getController(name: "connection")
            .addView(action: "show-contacts", view: ShowContactsView())
    }
    
    @IBAction func onLogin(_ sender: Any) {
        let connection = Mvc.getInstance().getModel().get(name: "connection") as! Model
        connection.set(name: "username", value: usernameView.text!)
        connection.set(name: "password", value: passwordView.text!)
        SocketProxy.getInstance().connectToServer()
    }
}

