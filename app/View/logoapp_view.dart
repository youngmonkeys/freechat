import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';

import '../ViewModel/logoapp_view_model.dart';
import '../common/color_extentions.dart';
import '../images/images_extention.dart';

class LogoappView extends StatefulWidget {
  const LogoappView({super.key});

  @override
  State<LogoappView> createState() => _LogoappViewState();
}

class _LogoappViewState extends State<LogoappView> {
  late LogoappViewModel controller;

  @override
  void initState() {
    super.initState();
    SystemChrome.setEnabledSystemUIMode(SystemUiMode.leanBack);
    controller = Get.put(LogoappViewModel());
    controller.loadView();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: TColor.bg,
      body: Center(
        child: Image.asset(
          ImagesAssset.logoApp,
          height: 250,
        ),
      ),
    );
  }
}
