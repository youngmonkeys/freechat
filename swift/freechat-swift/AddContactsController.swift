//
//  ContactViewController.swift
//  hello-swift
//
//  Created by Dung Ta Van on 10/29/18.
//  Copyright © 2018 Young Monkeys. All rights reserved.
//

import UIKit

class AddContactsController:
    UIViewController,
    UITableViewDataSource, UITableViewDelegate, UISearchBarDelegate {

    private var contactDatas: [AddContactCellData] = [];
    
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var contactTable: UITableView!
    @IBOutlet weak var searchBar: UISearchBar!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        contactTable.dataSource = self
        contactTable.delegate = self
        searchBar.delegate = self
        let mvc = Mvc.getInstance()
        let connectionModel = mvc.getModel().get(name: "connection") as! Model
        usernameLabel.text = connectionModel.get(name: "username") as? String
        let controller = mvc.getController(name: "search-contact")
        controller.addView(action: "search-contacts",
                           view: SearchContactsView(parent: self))
        controller.addView(action: "add-contacts",
                           view: AddContactsView(navigationController: self.navigationController))
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
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let contactData = contactDatas[indexPath.row]
        SocketRequests.sendAddContacts(user: contactData.username)
    }
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        SocketRequests.sendSearchContacts(keyword: searchText)
    }
    
    @IBAction func onLogin(_ sender: Any) {
        ViewManager.getInstance().showContactsView()
    }
    
    class AddContactsView: View {
        unowned let navigationController: UINavigationController?
        init(navigationController: UINavigationController?) {
            self.navigationController = navigationController
        }
        func update(component: String, data: Any) {
            navigationController?.popViewController(animated: true)
        }
    }
    
    class SearchContactsView: View {
        unowned let parent: AddContactsController
        init(parent: AddContactsController) {
            self.parent = parent
        }
        func update(component: String, data: Any) {
            parent.contactDatas.removeAll()
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
}
