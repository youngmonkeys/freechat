import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import '../ViewModel/login_view_model.dart';
import '../images/images_extention.dart';
import '../test.dart';
import 'chat_view.dart';
import '../common/color_extentions.dart';
import '../common_widget/globals.dart';
import 'main_screen_view.dart';

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
              const SizedBox(height: 80),
              //login text
              _textLogin(),
              const SizedBox(height: 120),
              Obx(
                () => _formUsernameAndPassword(
                    loginController), //Returns a form that includes two input fields for username and password
              ),
              const SizedBox(height: 40),
              _elevatedButtonLogin(loginController, context), // button login
              const SizedBox(
                height: 60,
              ),
              _textOrContinueWith(),
              const SizedBox(
                height: 30,
              ),
              _logoAppleAndGoogle(),
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

  Row _logoAppleAndGoogle() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Image.asset(
          ImagesAssset.logoApple,
          height: 100,
        ),
        const SizedBox(
          width: 20,
        ),
        Image.asset(
          ImagesAssset.logoGG,
          height: 90,
        ),
      ],
    );
  }

  Padding _textOrContinueWith() {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 25.0),
      child: Row(
        children: [
          Expanded(
            child: Divider(
              thickness: 0.5,
              color: Colors.grey[400],
            ),
          ),
          const Padding(
            padding: const EdgeInsets.symmetric(horizontal: 10.0),
            child: Text(
              'Or continue with',
              style: TextStyle(color: Color(0xFF616161)),
            ),
          ),
          Expanded(
            child: Divider(
              thickness: 0.5,
              color: Colors.grey[400],
            ),
          ),
        ],
      ),
    );
  }

  //form input fields for username and password
  Form _formUsernameAndPassword(ViewModel loginController) {
    return Form(
      key: loginController.formKey,
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 25),
        child: Column(
          children: [
            _formUsername(loginController),
            const SizedBox(height: 20),
            _formPassword(loginController),
          ],
        ),
      ),
    );
  }

  //button login
  ElevatedButton _elevatedButtonLogin(
      ViewModel loginController, BuildContext context) {
    return ElevatedButton(
      onPressed: () {
        alert_dialog = false;
        if (loginController.formKey.currentState!.validate()) {
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => MainScreenView(
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
          borderRadius: BorderRadius.circular(30), // Đặt bo góc cho nút
        ),
      ),
      child: const Icon(
        Icons.door_front_door_outlined,
        color: Colors.lightBlueAccent,
      ),
    );
  }

  //form input field for password
  TextFormField _formPassword(ViewModel loginController) {
    return TextFormField(
      controller: password,
      obscureText: loginController.isObscured.value,
      textAlign: TextAlign.center,
      decoration: InputDecoration(
        border: const OutlineInputBorder(),
        enabledBorder: const OutlineInputBorder(
          borderSide: BorderSide(color: Colors.lightBlueAccent, width: 0.0),
        ),
        focusedBorder: const OutlineInputBorder(
          borderSide: BorderSide(color: Colors.blueAccent, width: 2.0),
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
      style:
          TextStyle(color: TColor.primaryText28, fontWeight: FontWeight.bold),
    );
  }

  //form field for username
  TextFormField _formUsername(ViewModel loginController) {
    return TextFormField(
      controller: username,
      obscureText: false,
      textAlign: TextAlign.center,
      decoration: InputDecoration(
          border: const OutlineInputBorder(),
          enabledBorder: const OutlineInputBorder(
            borderSide: BorderSide(color: Colors.lightBlueAccent, width: 0.0),
          ),
          focusedBorder: const OutlineInputBorder(
            borderSide: BorderSide(color: Colors.blueAccent, width: 2.0),
          ),
          hintText: 'Username',
          hintStyle: TextStyle(color: TColor.primaryText28),
          suffixIcon: const Icon(Icons.person)),
      onChanged: loginController.onChangeUsername,
      validator: loginController.validatorUsername,
      style:
          TextStyle(color: TColor.primaryText28, fontWeight: FontWeight.bold),
    );
  }

  //login text
  Text _textLogin() {
    return Text(
      'Login',
      style: TextStyle(
        fontSize: 60,
        fontWeight: FontWeight.bold,
        color: TColor.primaryText28,
      ),
    );
  }
}
