import 'package:flutter/material.dart';

import '../../common/images_extention.dart';

class Logo extends StatelessWidget {
  const Logo({super.key});

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
