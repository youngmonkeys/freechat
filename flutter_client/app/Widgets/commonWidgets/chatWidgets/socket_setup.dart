import '../../../Model/socket_proxy.dart';
import '../../../globals.dart';
import 'package:flutter/material.dart';

import '../../../main.dart';

void setup(
  SocketProxy socketProxy,
  Function setState,
  BuildContext context,
  Function(String) setUser,
) async {
  socketProxy.onDisconnected(() {
    setState(() {});
  });

  socketProxy.onConnectionFailed(() {
    setState(() {});
  });

  socketProxy.onSecureChat((message) {
    // Chỉ xử lý tin nhắn chatbot khi đang trò chuyện với Chat Bot
    setState(() {
      messages.add({
        'from': 'Chat Bot',
        'to': 'user',
        'message': message,
      });
    });
  });

  socketProxy.onConnection(() {
    setState(() {});
  });

  socketProxy.onData(() {
    setState(() {});
  });

  socketProxy.onLoginError((bool alertDialog) {
    setState(() {
      alertDialog = true;
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => const MyApp()),
      );
    });
  });

  socketProxy.onChatBotResponse((question) {
    // Chỉ xử lý phản hồi từ chatbot khi đang trò chuyện với Chat Bot
    setState(() {
      messages.add({
        'from': 'Chat Bot',
        'to': 'user',
        'message': question,
      });
    });
  });
}

void connect(SocketProxy socketProxy, String? username, String? password) {
  socketProxy.connectToServer(username!, password!);
}
