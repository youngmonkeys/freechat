// ignore_for_file: prefer_typing_uninitialized_variables

import 'package:ezyfox_server_flutter_client/ezy_clients.dart';
import "package:flutter/material.dart";
import "socket_proxy.dart";

class Chat extends StatefulWidget {
  const Chat({super.key, this.username, this.password});
  final username;
  final password;

  @override
  State<Chat> createState() => _ChatState();
}

List contacts = [];
List messages = [];

class _ChatState extends State<Chat> {
  var screen_selector = true;
  // true - list screen
  // false - user screen
  TextEditingController controller = TextEditingController();
  var user;
  var channel;

  setup() async {
    SocketProxy socketProxy = SocketProxy.getInstance();
    socketProxy.onDisconnected(() => {setState(() {})});
    socketProxy.onConnectionFailed(() => {setState(() {})});
    socketProxy.onSecureChat((message) => {setState(() {})});
    socketProxy.onConnection(() => {setState(() {})});
    socketProxy.onData(() => {setState(() {})});
  }

  connect() {
    setState(() {
      SocketProxy.getInstance()
          .connectToServer(widget.username, widget.password);
    });
  }

  @override
  Widget build(BuildContext context) {
    setup();
    connect();
    return screen_selector
        ? Scaffold(
            appBar: AppBar(
              title: const Text("Contacts"),
              automaticallyImplyLeading: false,
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
                          return OutlinedButton(
                              onPressed: () {
                                setState(() {
                                  screen_selector = false;
                                  user = contacts[index]['users'][0].toString();
                                  channel = contacts[index]['channelId'];
                                });
                              },
                              child: ListTile(
                                title: Text(
                                    contacts[index]['users'][0].toString()),
                              ));
                        },
                      );
                    }),
                  )
                ],
              ),
            ),
          )
        : Scaffold(
            appBar: AppBar(
              title: Text(user),
              leading: Builder(
                builder: (BuildContext context) {
                  return IconButton(
                    icon: const Icon(Icons.arrow_back),
                    onPressed: () {
                      setState(() {
                        screen_selector = true;
                      });
                    },
                    tooltip:
                        MaterialLocalizations.of(context).openAppDrawerTooltip,
                  );
                },
              ),
            ),
            body: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Expanded(
                      flex: 5,
                      child: Builder(builder: (context) {
                        return ListView.builder(
                          itemCount: messages.length,
                          itemBuilder: (context, index) {
                            return Padding(
                              padding: const EdgeInsets.all(2.0),
                              child: Container(
                                decoration: BoxDecoration(
                                  borderRadius: BorderRadius.circular(10),
                                  color: (messages[index]['from'] == 'user')
                                      ? Colors.black12
                                      : Colors.lightBlueAccent,
                                ),
                                child: ((messages[index]['from'] == user) ||
                                        (messages[index]['to'] == user))
                                    ? ListTile(
                                        title: Container(
                                        alignment:
                                            (messages[index]['from'] == 'user')
                                                ? Alignment.centerRight
                                                : Alignment.centerLeft,
                                        width:
                                            MediaQuery.of(context).size.width *
                                                2 /
                                                3,
                                        child: Text(messages[index]['message']
                                            .toString()),
                                      ))
                                    : null,
                              ),
                            );
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
                        Expanded(
                            flex: 4,
                            child: Container(
                              decoration: BoxDecoration(
                                  borderRadius: BorderRadius.circular(5),
                                  color: Colors.black12),
                              child: Padding(
                                padding: const EdgeInsets.all(8.0),
                                child: TextField(
                                  controller: controller,
                                  keyboardType: TextInputType.multiline,
                                  maxLines: 3,
                                ),
                              ),
                            )),
                        const VerticalDivider(),
                        Expanded(
                          child: Container(
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(5),
                              color: Colors.lightBlueAccent,
                            ),
                            child: IconButton(
                              onPressed: () {
                                var app = EzyClients.getInstance()
                                    .getDefaultClient()
                                    .zone!
                                    .appManager
                                    .getAppByName("freechat");
                                var data = {};
                                data["channelId"] = channel;
                                data["message"] = controller.text.toString();
                                var data_messages = [];
                                data_messages = [
                                  {
                                    'from': 'user',
                                    'to': user,
                                    'message': controller.text.toString()
                                  }
                                ];
                                messages = messages + data_messages;
                                controller.text = '';
                                setState(() {
                                  app?.send("6", data);
                                });
                              },
                              icon: const Icon(Icons.send),
                            ),
                          ),
                        ),
                      ],
                    )
                  ],
                ),
              ),
            ),
          );
  }
}
