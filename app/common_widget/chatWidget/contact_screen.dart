// ignore_for_file: deprecated_member_use

import 'package:flutter/material.dart';
import 'package:flutter_exit_app/flutter_exit_app.dart';
import '../../Model/socket_proxy.dart';
import '../../common/color_extentions.dart';
import '../../globals.dart';
import '../../images/images_extention.dart';
import '../../main.dart';
import 'search_contacts.dart'; // Điều chỉnh đường dẫn nếu cần

class ContactScreen extends StatefulWidget {
  final Function(String, int) onUserSelected;
  const ContactScreen({super.key, required this.onUserSelected});

  @override
  // ignore: library_private_types_in_public_api
  _ContactScreenState createState() => _ContactScreenState();
}

class _ContactScreenState extends State<ContactScreen> {
  bool screenSelector = false;
  String user = '';
  int channel = 0;

  @override
  Widget build(BuildContext context) {
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
          _iconPushSearchContact(context),
          _iconCancelQuitLogout(context),
        ],
      ),
      body: Center(
        child: Column(mainAxisAlignment: MainAxisAlignment.center, children: [
          Expanded(
            child: ListView(
              children: [
                _chatbot(),
                const Divider(
                  color: Colors.lightBlueAccent,
                  thickness: 1,
                ),
                _chatGPT(),
                const Divider(
                  color: Colors.lightBlueAccent,
                  thickness: 1,
                ),
                ...List.generate(contacts.length, (index) {
                  return _user(index);
                }),
              ],
            ),
          ),
        ]),
      ),
    );
  }

  Column _user(int index) {
    return Column(
      children: [
        ElevatedButton(
          style: ButtonStyle(
            backgroundColor: MaterialStateProperty.all(
              Colors.transparent,
            ),
            shadowColor: MaterialStateProperty.all(
              Colors.transparent,
            ),
          ),
          onPressed: () {
            widget.onUserSelected(contacts[index]['users'][0].toString(),
                channel = contacts[index]['channelId']);
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
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          contacts[index]['users'][0].toString(),
                          style: TextStyle(
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                              color: TColor.primaryText60),
                        ),
                        Text(
                          'Hi, I using FreeChat',
                          style: TextStyle(
                              fontSize: 16, color: TColor.primaryText28),
                        ),
                      ],
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
        ),
        const Divider(
          color: Colors.lightBlueAccent,
          thickness: 1,
        ),
      ],
    );
  }

  ElevatedButton _chatGPT() {
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
        widget.onUserSelected('Chat GPT', 1);
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
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Chat GPT',
                      style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                          color: TColor.primaryText60),
                    ),
                    Text(
                      'Hi, I\'am Chat Ai',
                      style:
                          TextStyle(fontSize: 16, color: TColor.primaryText28),
                    ),
                  ],
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

  ElevatedButton _chatbot() {
    return ElevatedButton(
      style: ButtonStyle(
        backgroundColor: WidgetStateProperty.all(
          Colors.transparent,
        ),
        shadowColor: MaterialStateProperty.all(
          Colors.transparent,
        ),
      ),
      onPressed: () {
        widget.onUserSelected('Chat Bot', 1);
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
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Chat Bot',
                      style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                          color: TColor.primaryText60),
                    ),
                    Text(
                      'Hi, I\'am Chat Bot',
                      style:
                          TextStyle(fontSize: 16, color: TColor.primaryText28),
                    ),
                  ],
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

  IconButton _iconCancelQuitLogout(BuildContext context) {
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

  IconButton _iconPushSearchContact(BuildContext context) {
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
