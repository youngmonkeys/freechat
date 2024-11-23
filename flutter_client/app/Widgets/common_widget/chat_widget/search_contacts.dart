import 'package:flutter/material.dart';
import 'package:get/get.dart';
import '../../../globals.dart';
import '../../common/color_extention.dart';

class SearchContacts extends StatefulWidget {
  const SearchContacts({super.key});

  @override
  State<SearchContacts> createState() => _SearchContactsState();
}

class _SearchContactsState extends State<SearchContacts> {
  TextEditingController controller = TextEditingController();
  RxList<dynamic> filteredContacts = <dynamic>[].obs; // Danh sách người dùng được lọc

  @override
  void initState() {
    super.initState();
    controller.text = '';
    suggestions = [];
    // Ban đầu, filteredContacts chứa tất cả danh sách contacts
    filteredContacts.value = contacts;
  }

  // Hàm để lọc danh sách dựa trên văn bản trong TextField
  void filterContacts(String query) {
    if (query.isNotEmpty) {
      // Nếu có văn bản trong TextField, lọc các contacts chứa chuỗi văn bản đó
      filteredContacts.value = contacts
          .where((contact) => contact
          .toLowerCase()
          .contains(query.toLowerCase())) // So sánh không phân biệt chữ hoa chữ thường
          .toList();
    } else {
      // Nếu TextField trống, hiển thị tất cả contacts
      filteredContacts.value = contacts;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: TColor.bg,
        title: const Text(
          "Search",
          style: TextStyle(color: Colors.white),
        ),
        leading: Builder(
          builder: (BuildContext context) {
            return IconButton(
              icon: const Icon(
                Icons.arrow_back,
                color: Colors.white,
              ),
              onPressed: () => Navigator.pop(context, 'Cancel'),
              tooltip: MaterialLocalizations.of(context).openAppDrawerTooltip,
            );
          },
        ),
      ),
      body: Container(
        color: TColor.bg,
        child: Column(
          children: [
            // TextField để tìm kiếm
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextField(
                controller: controller,
                textAlign: TextAlign.center,
                style: const TextStyle(
                  color: Colors.white, // Màu chữ khi nhập
                  fontSize: 16.0, // Kích thước chữ
                ),
                decoration: InputDecoration(
                  hintText: 'Enter username...',
                  hintStyle: TextStyle(
                    color: TColor.primaryText35, // Màu chữ của hintText
                  ),
                  // Đường viền khi không focus
                  enabledBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(16.0), // Bo góc
                    borderSide: const BorderSide(
                      color: Colors.grey, // Màu viền khi không focus
                      width: 2.0, // Độ dày viền
                    ),
                  ),
                  // Đường viền khi được focus
                  focusedBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(16.0), // Bo góc
                    borderSide: const BorderSide(
                      color: Colors.blue, // Màu viền khi focus
                      width: 2.0,
                    ),
                  ),
                  // Có thể thêm các đường viền cho trạng thái lỗi nếu cần
                  errorBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(16.0),
                    borderSide: const BorderSide(
                      color: Colors.red, // Màu viền khi có lỗi
                      width: 2.0,
                    ),
                  ),
                ),
                onChanged: (value) {
                  filterContacts(value); // Gọi hàm lọc khi người dùng nhập văn bản
                },
              )
              ,
            ),
            // ListView hiển thị danh sách kết quả tìm kiếm
            Expanded(
              child: Obx(() {
                return ListView.builder(
                  itemCount: filteredContacts.length,
                  itemBuilder: (context, index) {
                    final username = filteredContacts[index]; // Sử dụng danh sách đã lọc
                    return ListTile(
                      title: Text(username, style: TextStyle(color: TColor.primaryText80),),
                      trailing: IconButton(
                        icon:  Icon(Icons.add, color: TColor.lightGray,),
                        onPressed: () {
                          if (!connectContacts.contains(username)) {
                            connectContacts.add(username);
                            print('Added user: $username to connectContacts');
                          } else {
                            print('User $username already in connectContacts');
                          }
                        },
                      ),
                      onTap: () {
                        if (!connectContacts.contains(username)) {
                          connectContacts.add(username);
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(content: Text('Đã chọn và thêm: $username')),
                          );
                        } else {
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(content: Text('$username đã được thêm')),
                          );
                        }
                      },
                    );
                  },
                );
              }),
            ),
          ],
        ),
      ),
    );
  }
}
