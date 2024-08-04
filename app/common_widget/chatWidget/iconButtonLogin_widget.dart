import 'package:flutter/material.dart';
import 'package:flutter_exit_app/flutter_exit_app.dart';

import '../../Model/socket_proxy.dart';
import '../../common/color_extentions.dart';
import '../../main.dart';

IconButton iconCancelQuitLogout(BuildContext context) {
  return IconButton(
    onPressed: () => showDialog<String>(
      context: context,
      barrierColor: TColor.bg,
      builder: (BuildContext context) => AlertDialog(
        backgroundColor: Colors.grey,
        title: const Text('Leaving application'),
        actions: <Widget>[
          Column(
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  TextButton(
                    onPressed: () {
                      FlutterExitApp.exitApp(iosForceExit: true);
                      FlutterExitApp.exitApp();
                    },
                    child: const Text(
                      'Quit',
                      style: TextStyle(color: Colors.lightBlueAccent),
                    ),
                  ),
                  TextButton(
                    onPressed: () {
                      SocketProxy.getInstance().disconnect();
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => const MyApp(),
                        ),
                      );
                    },
                    child: const Text(
                      'Logout',
                      style: TextStyle(color: Colors.lightBlueAccent),
                    ),
                  ),
                ],
              ),
              TextButton(
                onPressed: () => Navigator.pop(context, 'Cancel'),
                child: const Text(
                  'Cancel',
                  style: TextStyle(color: Colors.lightBlueAccent),
                ),
              ),
            ],
          ),
        ],
      ),
    ),
    icon: const Icon(
      Icons.logout_outlined,
      color: Colors.white,
    ),
  );
}
