import 'package:flutter/material.dart';

import '../common/color_extentions.dart';

class NewsView extends StatefulWidget {
  const NewsView({super.key});

  @override
  State<NewsView> createState() => _NewsViewState();
}

class _NewsViewState extends State<NewsView> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // backgroundColor: TColor.bg,
      body: Center(
        child: Text(
          'News',
          style: TextStyle(color: Colors.black, fontSize: 30),
        ),
      ),
    );
  }
}
