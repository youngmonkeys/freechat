import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../View/login_view.dart';

class LogoappViewModel extends GetxController {
  var scaffoldKey = GlobalKey<ScaffoldState>();

  void loadView(BuildContext context) async {
    await Future.delayed(const Duration(seconds: 3));
    Navigator.push(
      // ignore: use_build_context_synchronously
      context,
      MaterialPageRoute(builder: (context) => const MyHomePage()),
    );
  }
}
