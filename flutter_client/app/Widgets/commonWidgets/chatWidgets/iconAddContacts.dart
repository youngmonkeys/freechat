import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../../common/images_extention.dart';
import 'addConttacts.dart';

IconButton iconAddConttacts() {
  return IconButton(
      onPressed: () {
        Get.to(() => UserListScreen());
      },
      icon: Image.asset(
        ImagesAssset.add,
        height: 24,
        width: 24,
      ));
}
