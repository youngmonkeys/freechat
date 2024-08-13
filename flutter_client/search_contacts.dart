import 'dart:async';
import 'package:flutter/material.dart';
import 'package:ezyfox_server_flutter_client/ezy_clients.dart';

import 'globals.dart';

class SearchContacts extends StatefulWidget {
  const SearchContacts({super.key});

  @override
  State<SearchContacts> createState() => _SearchContactsState();
}

class _SearchContactsState extends State<SearchContacts> {
  TextEditingController controller = TextEditingController();
  handleTimeout() {
    setState(() {});
  }

  @override
  void initState() {
    controller.text = '';
    suggestions = [];
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.lightBlueAccent,
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
        color: Colors.white,
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
                    child: TextField(
                      controller: controller,
                      textAlign: TextAlign.center,
                    ),
                  ),
                  Expanded(
                      child: IconButton(
                          onPressed: () {
                            var app = EzyClients.getInstance()
                                .getDefaultClient()
                                .zone!
                                .appManager
                                .getAppByName("freechat");
                            var data = {};
                            if (controller.text.toString() == '') {
                              setState(() {
                                app?.send("1", data);
                              });
                            } else {
                              setState(() {
                                data['keyword'] = controller.text.toString();
                                app?.send("10", data);
                              });
                            }
                            Timer(const Duration(seconds: 1), handleTimeout);
                          },
                          icon: const Icon(Icons.search))),
                  const Expanded(flex: 1, child: SizedBox()),
                ],
              ),
            ),
            Expanded(
              flex: 15,
              child: Builder(builder: (context) {
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
                          title: Container(
                            alignment: Alignment.centerLeft,
                            width: MediaQuery.of(context).size.width * 2 / 3,
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Text(
                                  suggestions[index].toString(),
                                  style: const TextStyle(color: Colors.black),
                                ),
                                IconButton(
                                    onPressed: () {
                                      var app = EzyClients.getInstance()
                                          .getDefaultClient()
                                          .zone!
                                          .appManager
                                          .getAppByName("freechat");
                                      var data = {};
                                      data['target'] = [
                                        suggestions[index].toString()
                                      ];
                                      setState(() {
                                        app?.send("2", data);
                                      });
                                      Navigator.pop(context);
                                    },
                                    icon: const Icon(
                                      Icons.add,
                                      color: Colors.greenAccent,
                                    ))
                              ],
                            ),
                          ),
                        ),
                      ),
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
