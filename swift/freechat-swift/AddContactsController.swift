//
//  ContactViewController.swift
//  hello-swift
//
//  Created by Dung Ta Van on 10/29/18.
//  Copyright © 2018 Young Monkeys. All rights reserved.
//

import UIKit

class AddContactsController: UIViewController, UITableViewDataSource {

    private var contactDatas: [ContactCellData] = [];
    
    @IBOutlet weak var contactTable: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        contactTable.dataSource = self;
        let mvc = Mvc.getInstance()
        let controller = mvc.getController(name: "contact")
        controller.addView(action: "add-contacts", view: AddContactsView(parent: self))
        SocketRequests.sendGetContacts()
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
        cell.setUsername(value: contactData.getUsersString())
        cell.setLastMessage(value: contactData.lastMessage)
        return cell
    }
    
    class AddContactsView: View {
        unowned let parent: AddContactsController
        init(parent: AddContactsController) {
            self.parent = parent
        }
        func update(component: String, data: Any) {
            let contacts = data as! NSArray
            for contact in contacts {
                let c = contact as! NSDictionary
                parent.contactDatas.append(
                    ContactCellData(
                        channelId: c["channelId"] as! Int64,
                        users: c["users"] as! NSArray
                    )
                )
            }
            parent.contactTable.reloadData()
        }
    }
    
    @IBAction func onLogin(_ sender: Any) {
        ViewManager.getInstance().showContactsView()
    }
}
