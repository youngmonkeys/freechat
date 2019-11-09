//
//  ViewController.swift
//  hello-swift
//
//  Created by Dung Ta Van on 10/29/18.
//  Copyright © 2018 Young Monkeys. All rights reserved.
//

import UIKit

class ExHandshakeHandler : EzyHandshakeHandler {
    private let username : String
    private let password : String
    
    public init(username: String, password: String) {
        self.username = username
        self.password = password
    }
    
    override func getLoginRequest() -> NSArray {
        let array = NSMutableArray()
        array.add("freechat")
        array.add(username)
        array.add(password)
        return array
    }
};

class ExLoginSuccessHandler : EzyLoginSuccessHandler {
    override func handleLoginSuccess(responseData: NSObject) {
        let array = NSMutableArray()
        array.add("freechat")
        array.add(NSDictionary())
        client!.sendRequest(cmd: EzyCommand.APP_ACCESS, data: array)
    }
};

class ExAppAccessHandler : EzyAppAccessHandler {
    override func postHandle(app: EzyApp, data: NSObject) -> Void {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let contactView = storyboard.instantiateViewController(withIdentifier: "contactView") as! ContactViewController
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.window?.rootViewController = contactView
//        let array = NSMutableArray();
//        array.add(app.id);
//        client!.sendRequest(cmd: EzyCommand.APP_EXIT, data: array);
    }
};

class ExFirstAppResponseHandler : EzyAbstractAppDataHandler<NSDictionary> {
    override func process(app: EzyApp, data: NSDictionary) {
        let mvc = Mvc.getInstance()
        let controller = mvc?.getController(name: "contact")
        controller?.updateViews(action: "init", component: "contacts", data: data)
    }
};

class ViewController: UIViewController {

    private var client: EzyClient?
    
    @IBOutlet weak var usernameView: UITextField!
    @IBOutlet weak var passwordView: UITextField!
    
    public required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
        client = nil
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let mvc = Mvc.getInstance()
        mvc?.addController(name: "contact", controller: Controller())
        let clients = EzyClients.getInstance()!;
        let config = NSMutableDictionary()
        config["clientName"] = "first";
        config["zoneName"] = "freechat"
        client = clients.newDefaultClient(config: config)
        let setup = client!.setup!
            .addEventHandler(eventType: EzyEventType.CONNECTION_SUCCESS, handler: EzyConnectionSuccessHandler())
            .addEventHandler(eventType: EzyEventType.CONNECTION_FAILURE, handler: EzyConnectionFailureHandler())
            .addDataHandler(cmd: EzyCommand.LOGIN, handler: ExLoginSuccessHandler())
            .addDataHandler(cmd: EzyCommand.APP_ACCESS, handler: ExAppAccessHandler())
        _ = setup.setupApp(appName: "freechat")
            .addDataHandler(cmd: "5", handler: ExFirstAppResponseHandler())
        Thread.current.name = "main";
        clients.processEvents()
    }
    
    @IBAction func onLogin(_ sender: Any) {
        let username = usernameView.text!
        let password = passwordView.text!
        let handshaker = ExHandshakeHandler(username: username, password: password)
        _ = client!.setup!
            .addDataHandler(cmd: EzyCommand.HANDSHAKE, handler: handshaker)
//        let host = "192.168.1.13"
        let host = "ws.tvd12.com"
        client!.connect(host: host, port: 3005)
    }
}

