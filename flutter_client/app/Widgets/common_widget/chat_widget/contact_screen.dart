// ignore_for_file: deprecated_member_use
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import '../../../globals.dart';
import '../../common/color_extention.dart';
import '../../common/images_extention.dart';
import 'add_conttacts.dart';
import 'chatbot_widget.dart';
import 'icon_button_login_widget.dart';
import 'icon_push_search_contacts.dart';
import 'users_widget.dart';

class ContactScreen extends StatefulWidget {
  final Function(String, int) onUserSelected;
  const ContactScreen({super.key, required this.onUserSelected});

  @override
  // ignore: library_private_types_in_public_api
  _ContactScreenState createState() => _ContactScreenState();
}

class _ContactScreenState extends State<ContactScreen> {

  bool screenSelector = false;
  String user = '';
  int channel = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: TColor.bg,
      appBar: AppBar(
        title: const Text(
          "Contacts",
          style: TextStyle(color: Colors.white),
        ),
        automaticallyImplyLeading: false,
        backgroundColor: TColor.bg,
        actions: [
          iconPushSearchContact(context),
          IconButton(
              onPressed: () {
                print('chuyen den man addUser');
                Get.to(() => UserListScreen());
              },
              icon: Image.asset(
                ImagesAssset.add,
                height: 24,
                width: 24,
              )),
          iconCancelQuitLogout(context),
        ],
      ),
      body: Center(
        child: Column(mainAxisAlignment: MainAxisAlignment.center, children: [
          Expanded(
            child: Obx(() {
              return ListView(
                children: [
                  // Thêm chatbotWidget vào danh sách các widget để luôn hiển thị
                  chatbotWidget(widget.onUserSelected),
                  const Divider(
                    color: Colors.lightBlueAccent,
                    thickness: 1,
                  ),
                  // Kiểm tra nếu connectContacts2 không rỗng trước khi tạo các Widget người dùng
                  if (connectContacts2.isNotEmpty)
                    ...List.generate(connectContacts2.length, (index) {
                      // Lấy dữ liệu người dùng từ connectContacts2
                      var user = connectContacts2[index]['users'][0];
                      var channelId = connectContacts2[index]['channelId'];

                      // Truyền dữ liệu từ danh sách vào userWidget
                      return userWidget(
                        index,
                        (String selectedUser, int selectedChannelId) {
                          // Truyền dữ liệu từ danh sách vào hàm callback onUserSelected
                          widget.onUserSelected(user.toString(), channelId);
                        },
                      );
                    })
                  else
                    // Nếu connectContacts2 rỗng, hiển thị thông báo không có người dùng
                    Center(child: Text('Không có người dùng nào')),
                ],
              );
            }),
          )
        ]),
      ),
    );
  }
}
