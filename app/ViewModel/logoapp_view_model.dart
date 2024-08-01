import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../View/login_view.dart';

class LogoappViewModel extends GetxController {
  var scaffoldKey = GlobalKey<ScaffoldState>();

  void loadView() async {
    await Future.delayed(const Duration(seconds: 3));
    Get.to(() => const MyHomePage());
  }
}
