//
//  ContactTableViewCell.swift
//  hello-swift
//
//  Created by Dung Ta Van on 10/29/18.
//  Copyright © 2018 Young Monkeys. All rights reserved.
//

import UIKit

class AddContactCellData : NSObject {
    let username: String
    let fullName : String
    
    init(username: String, fullName: String) {
        self.username = username
        self.fullName = fullName
    }
}

class AddContactTableViewCell: UITableViewCell {

    @IBOutlet weak var avatarView: UIImageView!
    @IBOutlet weak var usernameView: UILabel!
    @IBOutlet weak var fullNameView: UILabel!
    
    func setUsername(value: String) -> Void {
        usernameView.text = value;
    }
    
    func setFullName(value: String) -> Void {
        fullNameView.text = value;
    }

}
