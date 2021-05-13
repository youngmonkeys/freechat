//
//  MessagesController.swift
//  freechat-swift
//
//  Created by Dzung on 11/05/2021.
//  Copyright © 2021 Young Monkeys. All rights reserved.
//

import UIKit
import MessageKit
import InputBarAccessoryView

struct Sender: SenderType {
    var senderId: String
    var displayName: String
}

struct Message: MessageType {
    var sender: SenderType
    var messageId: String
    var sentDate: Date
    var kind: MessageKind
}

class MessagesController:
    MessagesViewController,
    MessagesDataSource,
    MessagesLayoutDelegate,
    MessagesDisplayDelegate,
    InputBarAccessoryViewDelegate {
    
    var me: Sender!
    var otherUsers = [String: Sender]()
    var channelId: Int64 = 0
    var messages = [MessageType]()
    
    @IBOutlet weak var usernameLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        messagesCollectionView.messagesDataSource = self
        messagesCollectionView.messagesLayoutDelegate = self
        messagesCollectionView.messagesDisplayDelegate = self
        messageInputBar.delegate = self
        let mvc = Mvc.getInstance()
        let controller = mvc.getController(name: "message")
        controller.addView(action: "add-message", view: AddMessageView(parent: self))
        let chatModel = mvc.getModel().get(name: "chat") as! Model
        let connectionModel = mvc.getModel().get(name: "connection") as! Model
        let meUsername = connectionModel.get(name: "username") as! String
        let otherUsernames = chatModel.get(name: "users") as! NSArray
        channelId = chatModel.get(name: "channelId") as! Int64
        me = Sender(senderId: meUsername, displayName: meUsername)
        for otherUsername in (otherUsernames as! [String]) {
            otherUsers[otherUsername] = Sender(senderId: otherUsername, displayName: otherUsername)
        }
    }
    
    func currentSender() -> SenderType {
        return me
    }
    
    func messageForItem(at indexPath: IndexPath, in messagesCollectionView: MessagesCollectionView) -> MessageType {
        return messages[indexPath.section]
    }
    
    func numberOfSections(in messagesCollectionView: MessagesCollectionView) -> Int {
        return messages.count
    }
    
    func inputBar(_ inputBar: InputBarAccessoryView, didPressSendButtonWith text: String) {
        let messageText = inputBar.inputTextView.text
        let message = Message(sender: me,
                              messageId: messageText!,
                              sentDate: Date(),
                              kind: .text(messageText!))
        addMessage(message: message)
        if(channelId == 0) {
            SocketRequests.sendBotMessage(message: messageText!)
        }
        else {
            SocketRequests.sendUserMessage(channelId: channelId, message: messageText!)
        }
        inputBar.inputTextView.text = ""
    }
    
    func addMessage(message: Message) {
        messages.append(message)
        messagesCollectionView.reloadData()
        messagesCollectionView.scrollToLastItem(animated: true)
    }
    
    class AddMessageView: View {
        unowned let parent: MessagesController
        init(parent: MessagesController) {
            self.parent = parent
        }
        func update(component: String, data: Any) {
            let dict = data as! Dictionary<String, Any>
            let from = dict["from"] as! String
            let messageText = dict["message"] as! String
            let sender = parent.otherUsers[from]
            let message = Message(sender: sender!,
                                  messageId: messageText,
                                  sentDate: Date(),
                                  kind: .text(messageText))
            parent.addMessage(message: message)
        }
    }
    
}



