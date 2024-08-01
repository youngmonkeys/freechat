import 'dart:async';

import 'package:flutter/material.dart';
import 'package:ezyfox_server_flutter_client/ezy_clients.dart';

import '../../common/color_extentions.dart';

class SearchContacts extends StatefulWidget {
  const SearchContacts({super.key});

  @override
  State<SearchContacts> createState() => _SearchContactsState();
}

class _SearchContactsState extends State<SearchContacts> {
  TextEditingController controller = TextEditingController();
  List<String> suggestions = []; // Đảm bảo bạn khởi tạo danh sách gợi ý

  void handleTimeout() {
    setState(() {});
  }

  @override
  void initState() {
    super.initState();
    controller.text = '';
    suggestions = [];
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _appbarSearch(),
      body: Container(
        color: TColor.bg,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Expanded(
              flex: 2,
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  const Expanded(flex: 2, child: SizedBox()),
                  Expanded(
                    flex: 6,
                    child: _formTextFieledSearch(),
                  ),
                  Expanded(
                    child: _iconButtonSearch(),
                  ),
                  const Expanded(
                    flex: 1,
                    child: SizedBox(),
                  ),
                ],
              ),
            ),
            Expanded(
              flex: 15,
              child: _listViewBuilder(),
            ),
          ],
        ),
      ),
    );
  }

  ListView _listViewBuilder() {
    return ListView.builder(
      itemCount: suggestions.length,
      itemBuilder: (context, index) {
        return Padding(
          padding: const EdgeInsets.all(2.0),
          child: Container(
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(10),
              border: Border.all(color: Colors.grey),
              color: Colors.white,
            ),
            child: ListTile(
              title: _containerListTitle(context, index),
            ),
          ),
        );
      },
    );
  }

  Container _containerListTitle(BuildContext context, int index) {
    return Container(
      alignment: Alignment.centerLeft,
      width: MediaQuery.of(context).size.width * 2 / 3,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(
            suggestions[index],
            style: const TextStyle(color: Colors.black),
          ),
          _iconButtonAdd(index, context),
        ],
      ),
    );
  }

  IconButton _iconButtonAdd(int index, BuildContext context) {
    return IconButton(
      onPressed: () {
        var app = EzyClients.getInstance()
            .getDefaultClient()
            .zone!
            .appManager
            .getAppByName("freechat");
        var data = {
          'target': [suggestions[index]]
        };
        setState(() {
          app?.send("2", data);
        });
        Navigator.pop(context);
      },
      icon: const Icon(
        Icons.add,
        color: Colors.greenAccent,
      ),
    );
  }

  TextField _formTextFieledSearch() {
    return TextField(
      controller: controller,
      textAlign: TextAlign.center,
      style: TextStyle(color: TColor.primaryText),
      decoration: InputDecoration(
        hintText: 'Search contacts...',
        hintStyle: TextStyle(color: TColor.primaryText28),
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(8.0),
        ),
        contentPadding: const EdgeInsets.symmetric(horizontal: 10.0),
      ),
    );
  }

  IconButton _iconButtonSearch() {
    return IconButton(
      onPressed: () {
        var app = EzyClients.getInstance()
            .getDefaultClient()
            .zone!
            .appManager
            .getAppByName("freechat");
        var data = {};
        if (controller.text.trim().isEmpty) {
          setState(() {
            app?.send("1", data);
          });
        } else {
          setState(() {
            data['keyword'] = controller.text.trim();
            app?.send("10", data);
          });
        }
        Timer(const Duration(seconds: 1), handleTimeout);
      },
      icon: const Icon(Icons.search),
    );
  }

  AppBar _appbarSearch() {
    return AppBar(
      backgroundColor: Colors.lightBlueAccent,
      title: const Text(
        "Search",
        style: TextStyle(color: Colors.white),
      ),
      leading: IconButton(
        icon: const Icon(
          Icons.arrow_back,
          color: Colors.white,
        ),
        onPressed: () => Navigator.pop(context, 'Cancel'),
        tooltip: MaterialLocalizations.of(context).openAppDrawerTooltip,
      ),
    );
  }
}
