// ignore_for_file: deprecated_member_use

import 'package:flutter/material.dart';

import '../../../globals.dart';
import '../../common/color_extention.dart';
import '../../common/images_extention.dart';

Widget userWidget(int index, Function(String, int) onUserSelected) {
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
          onUserSelected(contacts[index]['users'][0].toString(),
              contacts[index]['channelId']);
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
