// ignore_for_file: deprecated_member_use

import 'package:flutter/material.dart';

import '../../../globals.dart';
import '../../common/color_extention.dart';
import '../../common/images_extention.dart';

Widget userWidget(int index, Function(String, int) onUserSelected) {
  // Lấy dữ liệu người dùng từ connectContacts2 dựa trên index
  var userMap = connectContacts2[index];
  var users = userMap['users'];
  var channelId = userMap['channelId'];

  // Kiểm tra nếu danh sách users có dữ liệu
  if (users == null || users.isEmpty) {
    return SizedBox(); // Trả về widget rỗng nếu không có dữ liệu người dùng
  }

  var userName = users[0].toString(); // Lấy tên người dùng đầu tiên từ danh sách

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
          // Gọi hàm onUserSelected với tên người dùng và channelId
          onUserSelected(userName, channelId);
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
                        userName,
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                          color: TColor.primaryText60,
                        ),
                      ),
                      Text(
                        'Hi, I am using FreeChat',
                        style: TextStyle(
                          fontSize: 16,
                          color: TColor.primaryText28,
                        ),
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
