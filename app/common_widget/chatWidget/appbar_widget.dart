import 'package:flutter/material.dart';
import '../../common/color_extentions.dart';
import '../../images/images_extention.dart';
import '../../main.dart';

AppBar appbarChatScreen(
    BuildContext context, String? user, Function setState, bool alertDialog) {
  return AppBar(
    backgroundColor: TColor.bg,
    leadingWidth: 30,
    title: Row(
      mainAxisAlignment: MainAxisAlignment.start,
      children: [
        Container(
          height: 45,
          width: 45,
          decoration: BoxDecoration(
            shape: BoxShape.circle,
            image: DecorationImage(
              image: AssetImage(getUserImage(user)),
              fit: BoxFit.cover,
            ),
          ),
        ),
        const SizedBox(
          width: 10,
        ),
        Text(
          user ?? '',
          style: const TextStyle(
            color: Colors.white,
            fontSize: 18,
          ),
        ),
      ],
    ),
    actions: [
      iconPushSearchContacts(context),
      iconQuitLogoutCancel(context, alertDialog),
    ],
  );
}

String getUserImage(String? user) {
  if (user == 'Chat Bot') {
    return ImagesAssset.chatbot;
  } else if (user == 'Chat GPT') {
    return ImagesAssset.chatbot;
  } else {
    // Cần thay thế giá trị path đến ảnh của user thật
    return ImagesAssset.user;
  }
}

IconButton iconPushSearchContacts(BuildContext context) {
  return IconButton(
    onPressed: () async {},
    icon: const Icon(
      Icons.search,
      color: Colors.white,
    ),
  );
}

IconButton iconQuitLogoutCancel(BuildContext context, bool alertDialog) {
  return IconButton(
    onPressed: () {
      if (alertDialog == true) {
        Future.delayed(Duration.zero, () async {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => const MyApp()),
          );
        });
      } else {
        _showMyDialog(context, alertDialog);
      }
    },
    icon: const Icon(
      Icons.exit_to_app,
      color: Colors.white,
    ),
  );
}

Future<void> _showMyDialog(BuildContext context, bool alertDialog) async {
  return showDialog<void>(
    context: context,
    barrierDismissible: false,
    builder: (BuildContext context) {
      return AlertDialog(
        title: const Text('Cancel'),
        content: const SingleChildScrollView(
          child: ListBody(
            children: <Widget>[
              Text('Do you want to cancel this login?'),
            ],
          ),
        ),
        actions: <Widget>[
          TextButton(
            child: const Text('Yes'),
            onPressed: () {
              Navigator.of(context).pop();
              Future.delayed(Duration.zero, () async {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const MyApp()),
                );
              });
            },
          ),
          TextButton(
            child: const Text('No'),
            onPressed: () {
              Navigator.of(context).pop();
            },
          ),
        ],
      );
    },
  );
}
