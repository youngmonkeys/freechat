// // import 'package:flutter/material.dart';

// // class testapp extends StatelessWidget {
// //   const testapp({super.key, this.username, this.password});
// //   final String? username;
// //   final String? password;

// //   @override
// //   Widget build(BuildContext context) {
// //     return Scaffold(
// //       body: Center(
// //         child: Column(
// //           children: [
// //             const Text(
// //               'hello',
// //               style: TextStyle(fontSize: 30, color: Colors.black),
// //             ),
// //             Text(
// //               username ?? "khong co du lieu",
// //               style: const TextStyle(color: Colors.black, fontSize: 30),
// //             ),
// //             Text(password ?? "khong co du lieu"),
// //           ],
// //         ),
// //       ),
// //     );
// //   }
// // }

// // chat_screen.dart
// import 'package:ezyfox_server_flutter_client/ezy_clients.dart';
// import 'package:flutter/material.dart';

// import '../Model/socket_proxy.dart';
// import 'globals.dart';

// class ChatScreenView extends StatefulWidget {
//   final String user;
//   final int channel;

//   const ChatScreenView({Key? key, required this.user, required this.channel})
//       : super(key: key);

//   @override
//   _ChatScreenViewState createState() => _ChatScreenViewState();
// }

// class _ChatScreenViewState extends State<ChatScreenView> {
//   bool screenSelector = true; // true - list screen, false - chat screen
//   TextEditingController controller = TextEditingController();
//   String? user;
//   int? channel;
//   bool alertDialog = false;

//   @override
//   void initState() {
//     user = widget.user;
//     channel = widget.channel;
//     super.initState();
//   }

//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       backgroundColor: Colors.white,
//       appBar: AppBar(
//         backgroundColor: Colors.lightBlueAccent,
//         title: Text(
//           user ?? '',
//           style: const TextStyle(color: Colors.white),
//         ),
//         leading: Builder(
//           builder: (BuildContext context) {
//             return IconButton(
//               icon: const Icon(
//                 Icons.arrow_back,
//                 color: Colors.white,
//               ),
//               onPressed: () {
//                 setState(() {
//                   screenSelector = true;
//                 });
//               },
//               tooltip: MaterialLocalizations.of(context).openAppDrawerTooltip,
//             );
//           },
//         ),
//       ),
//       body: Center(
//         child: Column(
//           mainAxisAlignment: MainAxisAlignment.center,
//           children: [
//             Expanded(
//               flex: 5,
//               child: Builder(builder: (context) {
//                 return ListView.builder(
//                   itemCount: messages.length,
//                   itemBuilder: (context, index) {
//                     // Hiển thị tin nhắn từ chatbot chỉ khi người dùng đang trò chuyện với chatbot
//                     if (user == 'Chat Bot') {
//                       return Container(
//                         child: ((messages[index]['from'] == user) ||
//                                 (messages[index]['from'] == 'Bot') ||
//                                 (messages[index]['to'] == user))
//                             ? ListTile(
//                                 title: Container(
//                                   width: 100,
//                                   decoration: BoxDecoration(
//                                     border: Border.all(
//                                       color: (messages[index]['from'] == 'user')
//                                           ? Colors.white
//                                           : Colors.lightBlueAccent,
//                                     ),
//                                     borderRadius: BorderRadius.circular(10),
//                                     color: (messages[index]['from'] == 'user')
//                                         ? Colors.lightBlueAccent
//                                         : Colors.white,
//                                   ),
//                                   alignment: (messages[index]['from'] == 'user')
//                                       ? Alignment.centerRight
//                                       : Alignment.centerLeft,
//                                   child: Padding(
//                                     padding: const EdgeInsets.only(
//                                       left: 8.0,
//                                       top: 8.0,
//                                       right: 8.0,
//                                       bottom: 16.0,
//                                     ),
//                                     child: Text(
//                                       messages[index]['message'].toString(),
//                                       style: TextStyle(
//                                         color:
//                                             (messages[index]['from'] == 'user')
//                                                 ? Colors.white
//                                                 : Colors.black87,
//                                       ),
//                                     ),
//                                   ),
//                                 ),
//                               )
//                             : null,
//                       );
//                     } else {
//                       // Hiển thị tin nhắn từ người dùng khác và không hiển thị tin nhắn từ chatbot
//                       return Container(
//                         child: ((messages[index]['from'] == user) ||
//                                 (messages[index]['to'] == user))
//                             ? ListTile(
//                                 title: Container(
//                                   width: 100,
//                                   decoration: BoxDecoration(
//                                     border: Border.all(
//                                       color: (messages[index]['from'] == 'user')
//                                           ? Colors.white
//                                           : Colors.lightBlueAccent,
//                                     ),
//                                     borderRadius: BorderRadius.circular(10),
//                                     color: (messages[index]['from'] == 'user')
//                                         ? Colors.lightBlueAccent
//                                         : Colors.white,
//                                   ),
//                                   alignment: (messages[index]['from'] == 'user')
//                                       ? Alignment.centerRight
//                                       : Alignment.centerLeft,
//                                   child: Padding(
//                                     padding: const EdgeInsets.only(
//                                       left: 8.0,
//                                       top: 8.0,
//                                       right: 8.0,
//                                       bottom: 16.0,
//                                     ),
//                                     child: Text(
//                                       messages[index]['message'].toString(),
//                                       style: TextStyle(
//                                         color:
//                                             (messages[index]['from'] == 'user')
//                                                 ? Colors.white
//                                                 : Colors.black87,
//                                       ),
//                                     ),
//                                   ),
//                                 ),
//                               )
//                             : null,
//                       );
//                     }
//                   },
//                 );
//               }),
//             ),
//             const Divider(
//               color: Colors.transparent,
//               height: 5,
//             ),
//             Row(
//               children: [
//                 Expanded(
//                   flex: 4,
//                   child: Padding(
//                     padding: const EdgeInsets.all(8.0),
//                     child: Container(
//                       decoration: BoxDecoration(
//                         borderRadius: BorderRadius.circular(5),
//                         border: Border.all(color: Colors.lightBlueAccent),
//                         color: Colors.white,
//                       ),
//                       child: TextField(
//                         controller: controller,
//                         keyboardType: TextInputType.multiline,
//                         maxLines: 3,
//                         decoration: const InputDecoration(
//                           border: InputBorder.none,
//                         ),
//                       ),
//                     ),
//                   ),
//                 ),
//                 Expanded(
//                   child: Padding(
//                     padding: const EdgeInsets.all(8.0),
//                     child: Container(
//                       decoration: BoxDecoration(
//                         borderRadius: BorderRadius.circular(5),
//                         color: Colors.lightBlueAccent,
//                       ),
//                       child: IconButton(
//                         onPressed: () {
//                           if (controller.text.isNotEmpty) {
//                             var message = controller.text.toString();
//                             setState(() {
//                               messages.add({
//                                 'from': 'user',
//                                 'to': user,
//                                 'message': message,
//                               });
//                               controller.text = '';
//                             });

//                             // Gửi tin nhắn đến chatbot nếu người dùng đang chat với chatbot
//                             if (user == 'Chat Bot') {
//                               SocketProxy.getInstance()
//                                   .sendMessageToChatBot(message);
//                             } else {
//                               var app = EzyClients.getInstance()
//                                   .getDefaultClient()
//                                   .zone!
//                                   .appManager
//                                   .getAppByName("freechat");
//                               var data = {
//                                 "channelId": channel,
//                                 "message": message,
//                               };
//                               app?.send("6", data);
//                             }
//                           }
//                         },
//                         icon: const Icon(
//                           Icons.send,
//                           color: Colors.white,
//                         ),
//                       ),
//                     ),
//                   ),
//                 ),
//               ],
//             ),
//           ],
//         ),
//       ),
//     );
//   }
// }

