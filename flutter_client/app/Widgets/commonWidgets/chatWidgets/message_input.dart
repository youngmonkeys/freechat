import 'package:flutter/material.dart';

class MessageInput extends StatelessWidget {
  final TextEditingController controller;
  final VoidCallback onSendPressed;
  final VoidCallback startListening;
  final VoidCallback stopListening;
  final bool isListening;

  const MessageInput({
    super.key,
    required this.controller,
    required this.onSendPressed,
    required this.startListening,
    required this.stopListening,
    required this.isListening,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Expanded(
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
        ),
        IconButton(
          onPressed: () {
            if (isListening) {
              stopListening();
            } else {
              startListening();
            }
          },
          icon: Icon(
            isListening ? Icons.mic_off : Icons.mic,
            size: 40,
            color: isListening ? Colors.red : Colors.blue,
          ),
        ),
        Expanded(
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Container(
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(5),
                color: Colors.lightBlueAccent,
              ),
              child: IconButton(
                onPressed: onSendPressed,
                icon: const Icon(
                  Icons.send,
                  color: Colors.white,
                ),
              ),
            ),
          ),
        ),
      ],
    );
  }
}
