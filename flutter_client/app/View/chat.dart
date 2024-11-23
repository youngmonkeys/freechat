// ignore_for_file: unnecessary_null_comparison, avoid_print, unused_element

import 'package:flutter/material.dart';
import '../globals.dart';
import '../main.dart';
import '../Model/socket_proxy.dart';
import 'package:speech_to_text/speech_to_text.dart' as stt;

import '../widgets/common_widget/chat_widget/chat_screen.dart';
import '../widgets/common_widget/chat_widget/contact_screen.dart';

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
  final stt.SpeechToText _speechToText = stt.SpeechToText();
  bool _isListening = false;

  @override
  void initState() {
    super.initState();
    setup();
    connect();
    navigateToChatScreen(user ?? " ", channel ?? 1);
    navigateToContactScreen();
  }

  void _startListening() async {
    print('da vao den chuyen doi ghi am thanh van ban');
    if (!_isListening) {
      print('da vao den _isListening');
      setState(() {
        _isListening = true;
      });

      await _speechToText.listen(
        onResult: (result) {
          if (result.recognizedWords.isNotEmpty) {
            setState(() {
              controller.text = result.recognizedWords;
              _isListening = false;
            });
            print('Văn bản nhận diện: ${result.recognizedWords}');
          } else {
            print('Không có văn bản nhận diện được');
          }
        },
      );
    }
  }

  @override
  void dispose() {
    _speechToText.stop();
    super.dispose();
  }

  setup() async {
    SocketProxy socketProxy = SocketProxy.getInstance();

    socketProxy.onDisconnected(() {
      setState(() {});
    });

    socketProxy.onConnectionFailed(() {
      setState(() {});
    });

    socketProxy.onSecureChat((message) {
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
    if (selectedUser == null) return; // Kiểm tra nếu user là null

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
  Widget build(BuildContext context) {
    return screenSelector
        ? ContactScreen(
            onUserSelected: navigateToChatScreen,
          )
        : ChatScreen(
            user: user ?? " ",
            channel: channel ?? 0,
            onBackPressed: navigateToContactScreen,
            controller: controller,
          );
  }
}
