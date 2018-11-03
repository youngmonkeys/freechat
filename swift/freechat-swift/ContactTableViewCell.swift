//
//  ContactTableViewCell.swift
//  hello-swift
//
//  Created by Dung Ta Van on 10/29/18.
//  Copyright © 2018 Young Monkeys. All rights reserved.
//

import UIKit

class ContactCellData : NSObject {
    var username : String!
    var lastMessage : String!
    
    override init() {}
    
    init(username: String, lastMessage: String) {
        self.username = username;
        self.lastMessage = lastMessage;
    }
}

class ContactTableViewCell: UITableViewCell {

    @IBOutlet weak var avatarView: UIImageView!
    @IBOutlet weak var usernameView: UILabel!
    @IBOutlet weak var lastMessageView: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    func setUsername(value: String) -> Void {
        usernameView.text = value;
    }
    
    func setLastMessage(value: String) -> Void {
        lastMessageView.text = value;
    }

}
