import 'package:flutter/material.dart';

class testapp extends StatelessWidget {
  const testapp({super.key, this.username, this.password});
  final String? username;
  final String? password;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          children: [
            const Text(
              'hello',
              style: TextStyle(fontSize: 30, color: Colors.black),
            ),
            Text(
              username ?? "khong co du lieu",
              style: const TextStyle(color: Colors.black, fontSize: 30),
            ),
            Text(password ?? "khong co du lieu"),
          ],
        ),
      ),
    );
  }
}
