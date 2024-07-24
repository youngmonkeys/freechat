import 'package:flutter/widgets.dart';
import 'package:get/get.dart';

class ViewModel extends GetxController {
  RxBool isObscured = true.obs;
  final formKey = GlobalKey<FormState>();
  final showPassword = false.obs;
  final isLoading = false.obs;

  void toggleObscureText() {
    isObscured.value = !isObscured.value;
  }

  void showHidePassword() {
    showPassword.value = !showPassword.value;
  }

  void onChangeUsername(username) {
    formKey.currentState?.validate();
  }

  void onChangePassword(password) {
    formKey.currentState?.validate();
  }

  bool containsSpecialCharacters(String text) {
    final allowedSpecialCharacters = RegExp(r'[!#\$%^&*(),?":{}|<>]');
    return allowedSpecialCharacters.hasMatch(text);
  }

  String? validatorUsername(username) {
    if ((username ?? "").isEmpty) {
      return "Username không được để trống";
    } else if (containsSpecialCharacters(username!)) {
      return "Username không đúng định dạng";
    } else {
      return null;
    }
  }

  String? validatePassword(password) {
    if ((password ?? "").isEmpty) {
      return "Password không được để trống";
    } else {
      return null;
    }
  }
}
