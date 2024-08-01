import 'package:flutter/material.dart';
import '../../images/images_extention.dart';

class Logo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
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
}
