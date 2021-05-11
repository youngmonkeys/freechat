//
//  ContactViewController.swift
//  hello-swift
//
//  Created by Dung Ta Van on 10/29/18.
//  Copyright © 2018 Young Monkeys. All rights reserved.
//

import UIKit

class AddContactsController: UIViewController, UITableViewDataSource {

    private var contactDatas: [AddContactCellData] = [];
    
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var contactTable: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        contactTable.dataSource = self;
        let mvc = Mvc.getInstance()
        let connectionModel = mvc.getModel().get(name: "connection") as! Model
        usernameLabel.text = connectionModel.get(name: "username") as? String
        let controller = mvc.getController(name: "contact")
        controller.addView(action: "search-contacts", view: AddContactsView(parent: self))
        SocketRequests.sendGetSuggestContacts()
    }

    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return contactDatas.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "contactCell") as! AddContactTableViewCell
        let contactData = contactDatas[indexPath.row]
        cell.setUsername(value: contactData.username)
        cell.setFullName(value: contactData.fullName)
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
                    AddContactCellData(
                        username: c["username"] as! String,
                        fullName: c["fullName"] as! String
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
