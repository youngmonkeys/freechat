//
//  ContactViewController.swift
//  hello-swift
//
//  Created by Dung Ta Van on 10/29/18.
//  Copyright © 2018 Young Monkeys. All rights reserved.
//

import UIKit

class ContactViewController: UIViewController, UITableViewDataSource, View {

    private var contactDatas: [ContactCellData] = [];
    
    @IBOutlet weak var contactTable: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        contactTable.dataSource = self;
        let mvc = Mvc.getInstance()
        let controller = mvc?.getController(name: "contact")
        controller?.addView(action: "init", viewId: "self", view: self)
        let clients = EzyClients.getInstance()
        let client = clients?.getDefaultClient()
        let app = client?.zone?.getApp()
        let dict = NSMutableDictionary()
        dict["skip"] = 0;
        dict["limit"] = 100;
        app!.sendRequest(cmd: "5", data: dict)
    }
    
    func update(component: String, data: Any) {
        let contacts = data as! NSArray
        for contact in contacts {
            contactDatas.append(ContactCellData(username: contact as! String, lastMessage: "empty"))
        }
        contactTable.reloadData()
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return contactDatas.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "contactCell") as! ContactTableViewCell
        let contactData = contactDatas[indexPath.row]
        cell.setUsername(value: contactData.username)
        cell.setLastMessage(value: contactData.lastMessage)
        return cell
    }
}
