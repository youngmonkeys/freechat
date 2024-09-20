import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'view/logo_app_view.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      title: 'Chat Flutter',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const LogoappView(),
    );
  }
}
