import 'package:flutter/material.dart';

import '../../common/color_extention.dart';
import '../../common/images_extention.dart';

Widget chatbotWidget(Function(String, int) onUserSelected) {
  return Column(
    children: [
      ElevatedButton(
        style: ButtonStyle(
          backgroundColor: WidgetStateProperty.all(
            Colors.transparent,
          ),
          shadowColor: WidgetStateProperty.all(
            Colors.transparent,
          ),
        ),
        onPressed: () {
          onUserSelected('Chat Bot', 1);
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
      )
    ],
  );
}
