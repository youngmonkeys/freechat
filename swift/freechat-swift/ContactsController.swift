//
//  ContactViewController.swift
//  hello-swift
//
//  Created by Dung Ta Van on 10/29/18.
//  Copyright © 2018 Young Monkeys. All rights reserved.
//

import UIKit

class ContactsController:
    UIViewController,
    UITableViewDataSource, UITableViewDelegate, UISearchBarDelegate {

    private var contactDatas: [ContactCellData] = [];
    
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var contactTable: UITableView!
    @IBOutlet weak var searchBar: UISearchBar!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        searchBar.delegate = self
        contactTable.delegate = self
        contactTable.dataSource = self
        contactDatas.append(ContactCellData(channelId: 0, users: ["Bot"]))
        let mvc = Mvc.getInstance()
        let connectionModel = mvc.getModel().get(name: "connection") as! Model
        usernameLabel.text = connectionModel.get(name: "username") as? String
        let controller = mvc.getController(name: "contact")
        controller.addView(action: "add-contacts", view: AddContactsView(parent: self))
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
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

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if (tableView.cellForRow(at: indexPath) != nil) {
            let chatModel = Mvc.getInstance()
                .getModel()
                .get(name: "chat") as! Model
            chatModel.set(name: "channelId", value: contactDatas[indexPath.row].channelId)
            chatModel.set(name: "users", value: contactDatas[indexPath.row].users)
            ViewManager.getInstance().showMessagesView(navigationController: navigationController)
        }
    }
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        if(searchText.isEmpty) {
            SocketRequests.sendGetContacts()
        }
        else {
            SocketRequests.sendSearchExistedContacts(keyword: searchText)
        }
    }
    
    class AddContactsView: View {
        unowned let parent: ContactsController
        init(parent: ContactsController) {
            self.parent = parent
        }
        func update(component: String, data: Any) {
            parent.contactDatas.removeAll()
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
}
