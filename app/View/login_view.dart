import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import '../ViewModel/login_view_model.dart';
import '../chat.dart';
import '../common/color_extentions.dart';
import '../globals.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final TextEditingController username = TextEditingController();
  final TextEditingController password = TextEditingController();

  @override
  void initState() {
    super.initState();
    SystemChrome.setEnabledSystemUIMode(SystemUiMode.leanBack);
  }

  @override
  void dispose() {
    // Make sure to dispose of controllers to free up resources.
    username.dispose();
    password.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final loginController = Get.put(ViewModel());

    return Scaffold(
      backgroundColor: TColor.bg,
      body: SafeArea(
        child: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              const SizedBox(height: 50),
              Text(
                'Login',
                style: TextStyle(
                  fontSize: 60,
                  fontWeight: FontWeight.bold,
                  color: TColor.primaryText28,
                ),
              ),
              const SizedBox(height: 120),
              Obx(
                () => Form(
                  key: loginController.formKey,
                  child: Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 25),
                    child: Column(
                      children: [
                        TextFormField(
                          controller: username,
                          obscureText: false,
                          textAlign: TextAlign.center,
                          decoration: InputDecoration(
                              border: const OutlineInputBorder(),
                              enabledBorder: const OutlineInputBorder(
                                borderSide: BorderSide(
                                    color: Colors.lightBlueAccent, width: 0.0),
                              ),
                              focusedBorder: const OutlineInputBorder(
                                borderSide: BorderSide(
                                    color: Colors.blueAccent, width: 2.0),
                              ),
                              hintText: 'Username',
                              hintStyle: TextStyle(color: TColor.primaryText28),
                              suffixIcon: Icon(Icons.person)),
                          onChanged: loginController.onChangeUsername,
                          validator: loginController.validatorUsername,
                          style: TextStyle(
                              color: TColor.primaryText28,
                              fontWeight: FontWeight.bold),
                        ),
                        const SizedBox(height: 20),
                        TextFormField(
                          controller: password,
                          obscureText: loginController.isObscured.value,
                          textAlign: TextAlign.center,
                          decoration: InputDecoration(
                            border: const OutlineInputBorder(),
                            enabledBorder: const OutlineInputBorder(
                              borderSide: BorderSide(
                                  color: Colors.lightBlueAccent, width: 0.0),
                            ),
                            focusedBorder: const OutlineInputBorder(
                              borderSide: BorderSide(
                                  color: Colors.blueAccent, width: 2.0),
                            ),
                            hintText: 'Password',
                            hintStyle: TextStyle(color: TColor.primaryText28),
                            suffixIcon: IconButton(
                              icon: Icon(
                                loginController.isObscured.value
                                    ? Icons.visibility
                                    : Icons.visibility_off,
                              ),
                              onPressed: loginController.toggleObscureText,
                            ),
                          ),
                          onChanged: loginController.onChangePassword,
                          validator: loginController.validatePassword,
                          style: TextStyle(
                              color: TColor.primaryText28,
                              fontWeight: FontWeight.bold),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  alert_dialog = false;
                  if (loginController.formKey.currentState!.validate()) {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => Chat(
                          username: username.text,
                          password: password.text,
                        ),
                      ),
                    );
                  } else {
                    // Show error dialog
                    setState(() {
                      alert_dialog = true;
                    });
                  }
                },
                style: ElevatedButton.styleFrom(
                  backgroundColor: TColor.darkGray, // Đặt màu nền cho nút
                  elevation: 2, // Đặt độ cao cho nút
                  shadowColor: Colors.black, // Đặt màu bóng cho nút
                  padding: const EdgeInsets.symmetric(
                    horizontal: 40,
                    vertical: 10,
                  ), // Đặt kích thước padding cho nút
                  shape: RoundedRectangleBorder(
                    borderRadius:
                        BorderRadius.circular(30), // Đặt bo góc cho nút
                  ),
                ),
                child: const Icon(
                  Icons.door_front_door_outlined,
                  color: Colors.lightBlueAccent,
                ),
              ),
              alert_dialog
                  ? const Text(
                      "Invalid credentials.",
                      style: TextStyle(color: Colors.redAccent),
                    )
                  : Container(),
            ],
          ),
        ),
      ),
    );
  }
}
