import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import '../Widgets/common/color_extention.dart';
import '../Widgets/common/images_extention.dart';
import '../view_model/logo_app_view_model.dart';

class LogoappView extends StatefulWidget {
  const LogoappView({super.key});

  @override
  State<LogoappView> createState() => _LogoappViewState();
}

class _LogoappViewState extends State<LogoappView> {
  late LogoappViewmodel controller;

  @override
  void initState() {
    super.initState();
    SystemChrome.setEnabledSystemUIMode(SystemUiMode.leanBack);
    controller = Get.put(LogoappViewmodel());
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
