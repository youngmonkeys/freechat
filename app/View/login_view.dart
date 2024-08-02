import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import '../ViewModel/login_view_model.dart';
import '../common_widget/loginWidget/loginbutton.dart';
import '../common_widget/loginWidget/logo.dart';
import '../common_widget/loginWidget/text_or_continue_with.dart';
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
              _textLogin(), //login text
              const SizedBox(height: 120),
              Obx(
                () => _formUsernameAndPassword(
                    loginController), //Returns a form that includes two input fields for username and password
              ),
              const SizedBox(height: 40),
              // button login
              LoginButton(
                  loginController: loginController,
                  context: context,
                  usernameController: username,
                  passwordController: password,
                  onInvalidCredentials: () {
                    setState(() {
                      alert_dialog = true;
                    });
                  }),
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

  Logo _logoAppleAndGoogle() {
    return Logo();
  }

  OrContinueWith _textOrContinueWith() {
    return OrContinueWith();
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
