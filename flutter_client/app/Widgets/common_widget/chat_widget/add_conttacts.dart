import 'dart:convert';
import 'package:flutter/material.dart';
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

    _socketProxy.onUserList((users) {
      setState(() {});
    });

    _socketProxy.onConnectUserResponse((response) {
      if (response['status'] == 'success') {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Connected to ${response['username']}')),
        );
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
              content: Text('Failed to connect to ${response['username']}')),
        );
      }
    });

    _socketProxy.onContacts(() {
      setState(() {
        // Ensure that suggestions contains only strings
        if (suggestions.every((item) => item is String)) {
          _suggestedUsers = List<String>.from(suggestions);
        }
      });
    });

    _socketProxy.fetchUsersList();
  }

  void _addContact(String username) {
    _socketProxy.addContact(username);
  }

  void _connectToUser(String username) {
    _socketProxy.connectToUser(username);
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
            },
          ),
        ],
      ),
      body: contacts.isEmpty
          ? const Center(child: CircularProgressIndicator())
          : Column(
              children: [
                Expanded(
                  child: ListView.builder(
                    itemCount: contacts.length,
                    itemBuilder: (context, index) {
                      final username = contacts[index];
                      return ListTile(
                        title: Text(username),
                        trailing: IconButton(
                          icon: const Icon(Icons.message),
                          onPressed: () {
                            _connectToUser(username);
                          },
                        ),
                        onTap: () {
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(content: Text('Selected: $username')),
                          );
                        },
                      );
                    },
                  ),
                ),
                if (_suggestedUsers.isNotEmpty)
                  Container(
                    color: Colors.grey[200],
                    padding: const EdgeInsets.all(8.0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text(
                          'Suggested Contacts',
                          style: TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 16),
                        ),
                        const SizedBox(height: 8),
                        ..._suggestedUsers.map((suggestedUser) {
                          return ListTile(
                            title: Text(suggestedUser),
                            trailing: IconButton(
                              icon: const Icon(Icons.add),
                              onPressed: () {
                                _addContact(suggestedUser);
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
