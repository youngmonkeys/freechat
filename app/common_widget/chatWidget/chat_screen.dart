import 'package:ezyfox_server_flutter_client/ezy_clients.dart';
import 'package:flutter/material.dart';
import '../../Model/socket_proxy.dart';
import '../../common/color_extentions.dart';
import '../../globals.dart';
import '../../images/images_extention.dart';
import 'container_chatbot.dart';
import 'container_user.dart';
import 'message_input.dart';

class ChatScreen extends StatefulWidget {
  final String user;
  final int channel;
  final VoidCallback onBackPressed;
  final VoidCallback onRecordPressed;
  final TextEditingController controller;
  const ChatScreen({
    super.key,
    required this.user,
    required this.channel,
    required this.onBackPressed,
    required this.onRecordPressed,
    required this.controller,
  });

  @override
  // ignore: library_private_types_in_public_api
  _ChatScreenState createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen> {
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
                      // return _contaimerChatBot(index);
                      return ContainerChatbot(index: index, user: widget.user);
                    } else {
                      // return _containerUser(index);
                      return ContainerUser(index: index, user: widget.user);
                    }
                  },
                );
              }),
            ),
            const Divider(
              color: Colors.transparent,
              height: 5,
            ),
            MessageInput(
              controller: widget.controller,
              onSendPressed: () {
                if (widget.controller.text.isNotEmpty) {
                  var message = widget.controller.text.toString();
                  setState(() {
                    messages.add({
                      'from': 'user',
                      'to': widget.user,
                      'message': message,
                    });
                    widget.controller.text = '';
                  });

                  if (widget.user == 'Chat Bot') {
                    SocketProxy.getInstance().sendMessageToChatBot(message);
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
              onRecordPressed: widget.onRecordPressed,
            ),
            const SizedBox(
              height: 20,
            )
          ],
        ),
      ),
    );
  }
}
