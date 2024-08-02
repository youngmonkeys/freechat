import 'package:flutter/material.dart';
import 'package:ezyfox_server_flutter_client/ezy_clients.dart';

import '../../Model/socket_proxy.dart';
import '../../images/images_extention.dart';

// Sửa đổi hàm iconButtonSendMessage
Expanded iconButtonSendMessage(TextEditingController controller, String? user,
    int? channel, List messages, Function setState) {
  return Expanded(
    child: Padding(
      padding: const EdgeInsets.all(8.0),
      child: Container(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(5),
          color: Colors.lightBlueAccent,
        ),
        child: IconButton(
          onPressed: () {
            if (controller.text.isNotEmpty) {
              var message = controller.text.toString();
              setState(() {
                messages.add({
                  'from': 'user',
                  'to': user,
                  'message': message,
                });
                controller.text = '';
              });

              // Gửi tin nhắn đến chatbot nếu người dùng đang chat với chatbot
              if (user == 'Chat Bot') {
                SocketProxy.getInstance().sendMessageToChatBot(message);
              } else {
                var app = EzyClients.getInstance()
                    .getDefaultClient()
                    .zone!
                    .appManager
                    .getAppByName("freechat");
                var data = {
                  "channelId": channel,
                  "message": message,
                };
                app?.send("6", data);
              }
            }
          },
          icon: const Icon(
            Icons.send,
            color: Colors.white,
          ),
        ),
      ),
    ),
  );
}

// Sửa đổi hàm textFiledInputMessage
Expanded textFiledInputMessage(TextEditingController controller) {
  return Expanded(
    flex: 4,
    child: Padding(
      padding: const EdgeInsets.all(8.0),
      child: Container(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(5),
          border: Border.all(color: Colors.lightBlueAccent),
          color: Colors.white,
        ),
        child: TextField(
          controller: controller,
          keyboardType: TextInputType.multiline,
          maxLines: 3,
          decoration: const InputDecoration(
            border: InputBorder.none,
          ),
        ),
      ),
    ),
  );
}

// Sửa đổi hàm listViewBuilderUsers
Expanded listViewBuilderUsers(String? user, List messages) {
  return Expanded(
    flex: 5,
    child: ListView.builder(
      itemCount: messages.length,
      itemBuilder: (context, index) {
        return ((user == 'Chat Bot' &&
                    (messages[index]['from'] == user ||
                        messages[index]['from'] == 'Bot' ||
                        messages[index]['to'] == user)) ||
                (user != 'Chat Bot' &&
                    (messages[index]['from'] == user ||
                        messages[index]['to'] == user)))
            ? ListTile(
                title: Container(
                  width: 100,
                  decoration: BoxDecoration(
                    border: Border.all(
                      color: (messages[index]['from'] == 'user')
                          ? Colors.white
                          : Colors.lightBlueAccent,
                    ),
                    borderRadius: BorderRadius.circular(10),
                    color: (messages[index]['from'] == 'user')
                        ? Colors.lightBlueAccent
                        : Colors.white,
                  ),
                  alignment: (messages[index]['from'] == 'user')
                      ? Alignment.centerRight
                      : Alignment.centerLeft,
                  child: Padding(
                    padding: const EdgeInsets.only(
                      left: 8.0,
                      top: 8.0,
                      right: 8.0,
                      bottom: 16.0,
                    ),
                    child: Text(
                      messages[index]['message'].toString(),
                      style: TextStyle(
                        color: (messages[index]['from'] == 'user')
                            ? Colors.white
                            : Colors.black87,
                      ),
                    ),
                  ),
                ),
              )
            : Container();
      },
    ),
  );
}

// Sửa đổi hàm screenChatBot
ListTile screenChatBot(Function setState, Function(String) setUser,
    Function switchScreen, bool screenSelector) {
  return ListTile(
    leading: Container(
      width: 50,
      height: 50,
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        image: DecorationImage(
          image: AssetImage(ImagesAssset.chatbot),
          fit: BoxFit.cover,
        ),
      ),
    ),
    title: const Text(
      'Chat Bot',
      style: TextStyle(
        color: Colors.white,
        fontSize: 18,
      ),
    ),
    subtitle: const Text(
      "Chat Bot",
      style: TextStyle(
        color: Colors.white54,
      ),
    ),
    trailing: IconButton(
      icon: const Icon(
        Icons.chat,
        color: Colors.white,
      ),
      onPressed: () {
        setState(() {
          setUser('Chat Bot');
          screenSelector = false;
        });
        switchScreen();
      },
    ),
  );
}

// Sửa đổi hàm screenChatGPT
ListTile screenChatGPT(Function setState, Function(String) setUser,
    Function switchScreen, bool screenSelector) {
  return ListTile(
    leading: Container(
      width: 50,
      height: 50,
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        image: DecorationImage(
          image: AssetImage(ImagesAssset.chatbot),
          fit: BoxFit.cover,
        ),
      ),
    ),
    title: const Text(
      'Chat GPT',
      style: TextStyle(
        color: Colors.white,
        fontSize: 18,
      ),
    ),
    subtitle: const Text(
      "AI",
      style: TextStyle(
        color: Colors.white54,
      ),
    ),
    trailing: IconButton(
      icon: const Icon(
        Icons.chat,
        color: Colors.white,
      ),
      onPressed: () {
        setState(() {
          setUser('Chat GPT');
          screenSelector = false;
        });
        switchScreen();
      },
    ),
  );
}

// Sửa đổi hàm screenUser
ListTile screenUser(
    int index,
    Function setState,
    List contacts,
    Function(String) setUser,
    Function switchScreen,
    int channel,
    bool screenSelector) {
  return ListTile(
    leading: Container(
      width: 50,
      height: 50,
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        image: DecorationImage(
          image: AssetImage(ImagesAssset.user),
          fit: BoxFit.cover,
        ),
      ),
    ),
    title: Text(
      contacts[index]['users'][0].toString(),
      style: const TextStyle(
        color: Colors.white,
        fontSize: 18,
      ),
    ),
    subtitle: const Text(
      "Hey There I am Using Chat App",
      style: TextStyle(
        color: Colors.white54,
      ),
    ),
    trailing: IconButton(
      icon: const Icon(
        Icons.chat,
        color: Colors.white,
      ),
      onPressed: () {
        setState(() {
          setUser(contacts[index]['users'][0].toString());
          channel = contacts[index].channelId;
          screenSelector = false;
        });
        switchScreen();
      },
    ),
  );
}
