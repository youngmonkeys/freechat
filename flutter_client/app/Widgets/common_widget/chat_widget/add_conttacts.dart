// ignore_for_file: library_private_types_in_public_api

import 'package:flutter/material.dart';

import '../../../model/socket_proxy.dart';
import '../../../globals.dart';
// Đảm bảo bạn đã khai báo danh sách contacts ở đây

class UserListScreen extends StatefulWidget {
  const UserListScreen({super.key});

  @override
  _UserListScreenState createState() => _UserListScreenState();
}

class _UserListScreenState extends State<UserListScreen> {
  final SocketProxy _socketProxy = SocketProxy.getInstance();

  @override
  void initState() {
    super.initState();
    // Lấy danh sách người dùng từ server khi màn hình được tạo
    _socketProxy.onUserList((users) {
      setState(() {
        contacts = users; // Cập nhật danh sách người dùng toàn cục
      });
    });
    _socketProxy.getUsersList(); // Gửi yêu cầu lấy danh sách người dùng
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('User List'),
      ),
      body: contacts.isEmpty
          ? const Center(child: CircularProgressIndicator())
          : ListView.builder(
              itemCount: contacts.length,
              itemBuilder: (context, index) {
                final username = contacts[index];
                return ListTile(
                  title: Text(username),
                  onTap: () {
                    // Xử lý sự kiện khi nhấn vào một người dùng, ví dụ:
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(content: Text('Selected: $username')),
                    );
                  },
                );
              },
            ),
    );
  }
}
