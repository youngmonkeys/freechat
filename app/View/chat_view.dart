// ignore_for_file: deprecated_member_use
import 'package:ezyfox_server_flutter_client/ezy_clients.dart';
import 'package:flutter/material.dart';
import 'package:flutter_exit_app/flutter_exit_app.dart';
import '../common/color_extentions.dart';
import '../common_widget/chatWidget/globals.dart';
import '../images/images_extention.dart';
import '../main.dart';
import '../common_widget/chatWidget/search_contacts.dart';
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

  @override
  void initState() {
    setup();
    connect();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return screenSelector ? _contactScreen(context) : _chatScreen();
  }

  Scaffold _chatScreen() {
    return Scaffold(
      backgroundColor: TColor.bg,
      appBar: _appbarChatScreen(),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            _listViewBuilderUsers(),
            const Divider(
              color: Colors.transparent,
              height: 5,
            ),
            Row(
              children: [
                _textFiledInputMessage(),
                _iconButtonSendMessage(),
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

  Expanded _iconButtonSendMessage() {
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

  Expanded _textFiledInputMessage() {
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

  Expanded _listViewBuilderUsers() {
    return Expanded(
      flex: 5,
      child: Builder(builder: (context) {
        return ListView.builder(
          itemCount: messages.length,
          itemBuilder: (context, index) {
            // Hiển thị tin nhắn từ chatbot chỉ khi người dùng đang trò chuyện với chatbot
            if (user == 'Chat Bot') {
              return Container(
                child: ((messages[index]['from'] == user) ||
                        (messages[index]['from'] == 'Bot') ||
                        (messages[index]['to'] == user))
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
            } else {
              // Hiển thị tin nhắn từ người dùng khác và không hiển thị tin nhắn từ chatbot
              return Container(
                child: ((messages[index]['from'] == user) ||
                        (messages[index]['to'] == user))
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
          },
        );
      }),
    );
  }

  AppBar _appbarChatScreen() {
    return AppBar(
      backgroundColor: TColor.bg,
      title: Row(
        children: [
          (user == 'Chat Bot' || user == 'Chat GPT')
              ? Image.asset(
                  ImagesAssset.chatbot,
                  height: 40,
                )
              : Image.asset(
                  ImagesAssset.user,
                  height: 40,
                ),
          Text(
            user ?? '',
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
              setState(() {
                screenSelector = true;
              });
            },
            tooltip: MaterialLocalizations.of(context).openAppDrawerTooltip,
          );
        },
      ),
    );
  }

  Scaffold _contactScreen(BuildContext context) {
    return Scaffold(
      backgroundColor: TColor.bg,
      appBar: AppBar(
        title: const Text(
          "Contacts",
          style: TextStyle(color: Colors.white),
        ),
        automaticallyImplyLeading: false,
        backgroundColor: TColor.bg,
        actions: [
          _iconPushSearchContacts(context),
          _iconQuitLogoutCancel(context),
        ],
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Expanded(
              child: Builder(builder: (context) {
                return ListView.builder(
                  itemCount: contacts.length,
                  itemBuilder: (context, index) {
                    return Column(
                      children: [
                        _screenChatBot(),
                        const Divider(
                          color: Colors.lightBlueAccent,
                          thickness: 1,
                        ),
                        _screenChatGPT(),
                        const Divider(
                          color: Colors.lightBlueAccent,
                          thickness: 1,
                        ),
                        _screenUser(index),
                        const Divider(
                          color: Colors.lightBlueAccent,
                          thickness: 1,
                        ),
                      ],
                    );
                  },
                );
              }),
            ),
          ],
        ),
      ),
    );
  }

  ElevatedButton _screenUser(int index) {
    return ElevatedButton(
      style: ButtonStyle(
        backgroundColor: MaterialStateProperty.all(
          Colors.transparent,
        ),
        shadowColor: MaterialStateProperty.all(
          Colors.transparent,
        ),
      ),
      onPressed: () {
        setState(() {
          screenSelector = false;
          user = contacts[index]['users'][0].toString();
          channel = contacts[index]['channelId'];
        });
        //   Get.to(ChatScreenView(
        //       user: contacts[index]['users'][0].toString(),
        //       channel: contacts[index]['channelId']));
      },
      child: ListTile(
        title: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Row(
              children: [
                ClipOval(
                  child: Image.asset(
                    ImagesAssset.user,
                    width: 50,
                    height: 50,
                    fit: BoxFit.cover,
                  ),
                ),
                const SizedBox(
                  width: 10,
                ),
                Text(
                  contacts[index]['users'][0].toString(),
                  style: TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                      color: TColor.primaryText60),
                ),
              ],
            ),
            const Icon(
              Icons.message_outlined,
              color: Colors.lightBlueAccent,
            ),
          ],
        ),
      ),
    );
  }

  ElevatedButton _screenChatGPT() {
    return ElevatedButton(
      style: ButtonStyle(
        backgroundColor: MaterialStateProperty.all(
          Colors.transparent,
        ),
        shadowColor: MaterialStateProperty.all(
          Colors.transparent,
        ),
      ),
      onPressed: () {
        setState(() {
          screenSelector = false;
          user = 'Chat GPT';
          channel = 1;
        });
        // Get.to(
        //     ChatScreenView(user: 'Chat Bot', channel: 1));
      },
      child: ListTile(
        title: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Row(
              children: [
                ClipOval(
                  child: Image.asset(
                    ImagesAssset.chatbot,
                    width: 54,
                    height: 54,
                    fit: BoxFit.cover,
                  ),
                ),
                const SizedBox(
                  width: 10,
                ),
                Text(
                  'Chat GPT',
                  style: TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                      color: TColor.primaryText60),
                ),
              ],
            ),
            const Icon(
              Icons.message_outlined,
              color: Colors.lightBlueAccent,
            ),
          ],
        ),
      ),
    );
  }

  ElevatedButton _screenChatBot() {
    return ElevatedButton(
      style: ButtonStyle(
        backgroundColor: MaterialStateProperty.all(
          Colors.transparent,
        ),
        shadowColor: MaterialStateProperty.all(
          Colors.transparent,
        ),
      ),
      onPressed: () {
        setState(() {
          screenSelector = false;
          user = 'Chat Bot';
          channel = 1;
        });
        // Get.to(
        //     ChatScreenView(user: 'Chat Bot', channel: 1));
      },
      child: ListTile(
        title: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Row(
              children: [
                ClipOval(
                  child: Image.asset(
                    ImagesAssset.chatbot,
                    width: 54,
                    height: 54,
                    fit: BoxFit.cover,
                  ),
                ),
                const SizedBox(
                  width: 10,
                ),
                Text(
                  'Chat Bot',
                  style: TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                      color: TColor.primaryText60),
                ),
              ],
            ),
            const Icon(
              Icons.message_outlined,
              color: Colors.lightBlueAccent,
            ),
          ],
        ),
      ),
    );
  }

  IconButton _iconQuitLogoutCancel(BuildContext context) {
    return IconButton(
      onPressed: () => showDialog<String>(
        context: context,
        barrierColor: TColor.bg,
        builder: (BuildContext context) => AlertDialog(
          backgroundColor: Colors.grey,
          title: const Text('Leaving application'),
          actions: <Widget>[
            Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    TextButton(
                      onPressed: () {
                        FlutterExitApp.exitApp(iosForceExit: true);
                        FlutterExitApp.exitApp();
                      },
                      child: const Text(
                        'Quit',
                        style: TextStyle(color: Colors.lightBlueAccent),
                      ),
                    ),
                    TextButton(
                      onPressed: () {
                        SocketProxy.getInstance().disconnect();
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => const MyApp(),
                          ),
                        );
                      },
                      child: const Text(
                        'Logout',
                        style: TextStyle(color: Colors.lightBlueAccent),
                      ),
                    ),
                  ],
                ),
                TextButton(
                  onPressed: () => Navigator.pop(context, 'Cancel'),
                  child: const Text(
                    'Cancel',
                    style: TextStyle(color: Colors.lightBlueAccent),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
      icon: const Icon(
        Icons.logout_outlined,
        color: Colors.white,
      ),
    );
  }

  IconButton _iconPushSearchContacts(BuildContext context) {
    return IconButton(
      onPressed: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => const SearchContacts(),
          ),
        );
      },
      icon: const Icon(
        Icons.search,
        color: Colors.white,
      ),
    );
  }
}
