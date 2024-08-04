import 'package:flutter/material.dart';

import '../../globals.dart';

class ContainerChatbot extends StatefulWidget {
  final int index;
  final String user;
  const ContainerChatbot({super.key, required this.index, required this.user});

  @override
  State<ContainerChatbot> createState() => _ContainerChatbotState();
}

class _ContainerChatbotState extends State<ContainerChatbot> {
  @override
  Widget build(BuildContext context) {
    return Container(
      child: ((messages[widget.index]['from'] == widget.user) ||
              (messages[widget.index]['from'] == 'Bot') ||
              (messages[widget.index]['to'] == widget.user))
          ? ListTile(
              title: Container(
                width: 100,
                decoration: BoxDecoration(
                  border: Border.all(
                    color: (messages[widget.index]['from'] == 'user')
                        ? Colors.white
                        : Colors.lightBlueAccent,
                  ),
                  borderRadius: BorderRadius.circular(10),
                  color: (messages[widget.index]['from'] == 'user')
                      ? Colors.lightBlueAccent
                      : Colors.white,
                ),
                alignment: (messages[widget.index]['from'] == 'user')
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
                    messages[widget.index]['message'].toString(),
                    style: TextStyle(
                      color: (messages[widget.index]['from'] == 'user')
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
