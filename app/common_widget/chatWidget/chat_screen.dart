import 'package:ezyfox_server_flutter_client/ezy_clients.dart';
import 'package:flutter/material.dart';
import '../../Model/socket_proxy.dart';
import '../../common/color_extentions.dart';
import '../../globals.dart';
import '../../images/images_extention.dart';

class ChatScreen extends StatefulWidget {
  final String user;
  final int channel;
  final VoidCallback onBackPressed;
  const ChatScreen({
    super.key,
    required this.user,
    required this.channel,
    required this.onBackPressed,
  });

  @override
  // ignore: library_private_types_in_public_api
  _ChatScreenState createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen> {
  final TextEditingController controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: TColor.bg,
      appBar: AppBar(
        backgroundColor: TColor.bg,
        title: Row(
          children: [
            (widget.user == 'Chat Bot' || widget.user == 'Chat GPT')
                ? Image.asset(
                    ImagesAssset.chatbot,
                    height: 40,
                  )
                : Image.asset(
                    ImagesAssset.user,
                    height: 40,
                  ),
            Text(
              widget.user,
              style: const TextStyle(color: Colors.white),
            ),
          ],
        ),
        leading: Builder(
          builder: (BuildContext context) {
            return IconButton(
              icon: const Icon(
                Icons.arrow_back,
                color: Colors.white,
              ),
              onPressed: () {
                widget.onBackPressed();
              },
              tooltip: MaterialLocalizations.of(context).openAppDrawerTooltip,
            );
          },
        ),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Expanded(
              flex: 5,
              child: Builder(builder: (context) {
                return ListView.builder(
                  itemCount: messages.length,
                  itemBuilder: (context, index) {
                    if (widget.user == 'Chat Bot') {
                      return _contaimerChatBot(index);
                    } else {
                      return _containerUser(index);
                    }
                  },
                );
              }),
            ),
            const Divider(
              color: Colors.transparent,
              height: 5,
            ),
            Row(
              children: [
                _formInputMessage(),
                Expanded(
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
                                'to': widget.user,
                                'message': message,
                              });
                              controller.text = '';
                            });

                            if (widget.user == 'Chat Bot') {
                              SocketProxy.getInstance()
                                  .sendMessageToChatBot(message);
                            } else {
                              var app = EzyClients.getInstance()
                                  .getDefaultClient()
                                  .zone!
                                  .appManager
                                  .getAppByName("freechat");
                              var data = {
                                "channelId": widget.channel,
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
                ),
              ],
            ),
            const SizedBox(
              height: 20,
            )
          ],
        ),
      ),
    );
  }

  Expanded _formInputMessage() {
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

  Container _containerUser(int index) {
    return Container(
      child: ((messages[index]['from'] == widget.user) ||
              (messages[index]['to'] == widget.user))
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
          : null,
    );
  }

  Container _contaimerChatBot(int index) {
    return Container(
      child: ((messages[index]['from'] == widget.user) ||
              (messages[index]['from'] == 'Bot') ||
              (messages[index]['to'] == widget.user))
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
          : null,
    );
  }
}
