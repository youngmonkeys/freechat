import "package:flutter/material.dart";
import "socket_proxy.dart";
import 'package:ezyfox_server_flutter_client/ezy_clients.dart';

class ChatMenu extends StatefulWidget {
  const ChatMenu({this.username, this.password});
  final username;
  final password;

  @override
  State<ChatMenu> createState() => _ChatMenuState();
}

List array = [];

class _ChatMenuState extends State<ChatMenu> {
  String socketState = "Socket has not connected yet";
  String sslMessage = "";

  Future<void> setup() async {
    SocketProxy socketProxy = SocketProxy.getInstance();
    socketProxy.onDisconnected(() => {
          setState(() {
            socketState = "Disconnected, retry ...";
            sslMessage = "";
          })
        });
    socketProxy.onConnectionFailed(() => {
          setState(() {
            socketState = "Can not connect to server";
            sslMessage = "";
          })
        });
    socketProxy.onSecureChat((message) => {
          setState(() {
            socketState = "Secure Chat";
            sslMessage = message;
          })
        });
    socketProxy.onConnection(() => {
          setState(() {
            socketState = "Connected";
            sslMessage = "";
          })
        });
  }

  void connect() {
    setState(() {
      SocketProxy.getInstance()
          .connectToServer(widget.username, widget.password);
      // freechat java client default credentials
    });
  }

  @override
  Widget build(BuildContext context) {
    setup();
    connect();
    return Scaffold(
      appBar: AppBar(
        title: Text("Contacts"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              "Socket message: ",
            ),
            Text(
              socketState,
              style: Theme.of(context).textTheme.headline6,
            ),
            const Text(
              "SSL message: ",
            ),
            Text(
              sslMessage,
              style: Theme.of(context).textTheme.headline6,
            ),
            Container(
              height: 300,
              width: double.infinity,
              child: Builder(builder: (context) {
                return ListView.builder(
                  itemCount: array.length,
                  itemBuilder: (context, index) {
                    return ListTile(
                      title: Text(array[index]['users'].toString()),
                    );
                  },
                );
              }),
            )
          ],
        ),
      ),
    );
  }
}
