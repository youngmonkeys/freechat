import 'package:flutter/material.dart';
import '../../View/chat_view.dart';
import '../../ViewModel/login_view_model.dart';
import '../../common/color_extentions.dart';

class LoginButton extends StatelessWidget {
  final ViewModel loginController;
  final BuildContext context;
  final TextEditingController usernameController;
  final TextEditingController passwordController;
  final VoidCallback onInvalidCredentials;

  const LoginButton({
    Key? key,
    required this.loginController,
    required this.context,
    required this.usernameController,
    required this.passwordController,
    required this.onInvalidCredentials,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      onPressed: () {
        if (loginController.formKey.currentState!.validate()) {
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => Chat(
                username: usernameController.text,
                password: passwordController.text,
              ),
            ),
          );
        } else {
          onInvalidCredentials();
        }
      },
      style: ElevatedButton.styleFrom(
        backgroundColor: TColor.darkGray,
        elevation: 2,
        shadowColor: Colors.black,
        padding: const EdgeInsets.symmetric(
          horizontal: 40,
          vertical: 10,
        ),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(30),
        ),
      ),
      child: const Icon(
        Icons.door_front_door_outlined,
        color: Colors.lightBlueAccent,
      ),
    );
  }
}
