import 'package:flutter/material.dart';
import '../common_widget/chatWidget/chat_screen.dart';
import '../common_widget/chatWidget/contact_screen.dart';
import '../globals.dart';
import '../main.dart';
import '../Model/socket_proxy.dart';

class Chat extends StatefulWidget {
  const Chat({super.key, this.username, this.password});
  final String? username;
  final String? password;

  @override
  State<Chat> createState() => _ChatState();
}

class _ChatState extends State<Chat> {
  bool screenSelector = true; // true - list screen, false - user screen
  TextEditingController controller = TextEditingController();
  String? user;
  int? channel;
  bool alertDialog = false;

  setup() async {
    SocketProxy socketProxy = SocketProxy.getInstance();

    socketProxy.onDisconnected(() {
      setState(() {});
    });

    socketProxy.onConnectionFailed(() {
      setState(() {});
    });

    socketProxy.onSecureChat((message) {
      // Chỉ xử lý tin nhắn chatbot khi đang trò chuyện với Chat Bot
      if (user == 'Chat Bot') {
        setState(() {
          messages.add({
            'from': 'Chat Bot',
            'to': 'user',
            'message': message,
          });
        });
      }
    });

    socketProxy.onConnection(() {
      setState(() {});
    });

    socketProxy.onData(() {
      setState(() {});
    });

    socketProxy.onLoginError(() {
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
      if (user == 'Chat Bot') {
        setState(() {
          messages.add({
            'from': 'Chat Bot',
            'to': 'user',
            'message': question,
          });
        });
      }
    });
  }

  connect() {
    setState(() {
      SocketProxy.getInstance()
          .connectToServer(widget.username!, widget.password!);
    });
  }

  void navigateToChatScreen(String selectedUser, int selectedChannel) {
    setState(() {
      screenSelector = false;
      user = selectedUser;
      channel = selectedChannel;
    });
  }

  void navigateToContactScreen() {
    setState(() {
      screenSelector = true;
    });
  }

  @override
  void initState() {
    setup();
    connect();
    navigateToChatScreen(user ?? " ", channel ?? 1);
    navigateToContactScreen();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return screenSelector
        ? ContactScreen(
            onUserSelected: navigateToChatScreen,
          )
        : ChatScreen(
            user: user ?? " ",
            channel: channel ?? 0,
            onBackPressed: navigateToContactScreen,
          );
  }
}
