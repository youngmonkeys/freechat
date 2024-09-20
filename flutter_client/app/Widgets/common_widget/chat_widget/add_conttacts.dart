import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';
import '../../../model/socket_proxy.dart';
import '../../../globals.dart';

class UserListScreen extends StatefulWidget {
  const UserListScreen({super.key});

  @override
  _UserListScreenState createState() => _UserListScreenState();
}

class _UserListScreenState extends State<UserListScreen> {
  final SocketProxy _socketProxy = SocketProxy.getInstance();

  List<String> _suggestedUsers = [];

  @override
  void initState() {
    super.initState();

    // Nhận danh sách users từ server và cập nhật state
    _socketProxy.onUserList((users) {
      setState(() {
        // Cập nhật danh sách contacts và loại bỏ trùng lặp
        contacts = [...users].toSet().toList();
        print('Updated contacts: $contacts');
      });
    });

    // Nhận danh sách gợi ý từ server
    _socketProxy.onContacts((suggestions) {
      setState(() {
        // Loại bỏ các user đã có trong danh sách contacts
        _suggestedUsers = List<String>.from(suggestions)
          ..removeWhere((user) => contacts.contains(user));
        print('Updated suggestions: $_suggestedUsers');
      });
    });

    // Fetch danh sách người dùng và gợi ý từ server khi khởi tạo
    _socketProxy.fetchUsersList();
    _socketProxy.fetchSuggestions();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('User List'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () {
              _socketProxy.fetchUsersList();
              _socketProxy.fetchSuggestions();
            },
          ),
        ],
      ),
      body: Column(
        children: [
          // Hiển thị danh sách người dùng từ contacts
          Expanded(
            child: Obx(() {
              return ListView.builder(
                itemCount: contacts.length,
                itemBuilder: (context, index) {
                  final username = contacts[index]; // Assuming contacts[index] is a Map
                  return ListTile(
                    title: Text(username),
                    trailing: IconButton(
                      icon: const Icon(Icons.message),
                      onPressed: () {
                        // _connectToUser(username);
                      },
                    ),
                    onTap: () {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(content: Text('Selected: $username')),
                      );
                    },
                  );
                },
              );
            })

          ),
          // Hiển thị danh sách gợi ý liên hệ
          if (_suggestedUsers.isNotEmpty)
            Container(
              color: Colors.grey[200],
              padding: const EdgeInsets.all(8.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text(
                    'Suggested Contacts',
                    style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
                  ),
                  const SizedBox(height: 8),
                  ..._suggestedUsers.map((suggestedUser) {
                    return ListTile(
                      title: Text(suggestedUser),
                      trailing: IconButton(
                        icon: const Icon(Icons.add),
                        onPressed: () {
                          // _addContact(suggestedUser);
                        },
                      ),
                    );
                  }).toList(),
                ],
              ),
            ),
        ],
      ),
    );
  }
}
