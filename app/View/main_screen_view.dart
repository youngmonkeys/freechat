import 'package:flutter/material.dart';

import '../common/color_extentions.dart';
import '../images/images_extention.dart';
import 'chat_view.dart';
import 'news_view.dart';

class MainScreenView extends StatefulWidget {
  const MainScreenView({super.key, this.username, this.password});
  final String? username;
  final String? password;

  @override
  State<MainScreenView> createState() => _MainScreenViewState();
}

class _MainScreenViewState extends State<MainScreenView>
    with SingleTickerProviderStateMixin {
  TabController? tabController;
  int selectTab = 0;

  @override
  void initState() {
    super.initState();
    tabController = TabController(length: 2, vsync: this);

    tabController?.addListener(() {
      selectTab = tabController?.index ?? 0;
      setState(() {});
    });
  }

  @override
  void dispose() {
    super.dispose();
    tabController?.dispose();
  }

  @override
  Widget build(BuildContext context) {
    // final controller = Get.put(dependency)
    return Scaffold(
      backgroundColor: TColor.bg,
      body: TabBarView(
        controller: tabController,
        children: [
          Chat(
            username: widget.username,
            password: widget.password,
          ),
          const NewsView()
        ],
      ),
      bottomNavigationBar: Container(
        decoration: BoxDecoration(color: TColor.bg, boxShadow: const [
          BoxShadow(color: Colors.black38, blurRadius: 5, offset: Offset(0, -3))
        ]),
        child: BottomAppBar(
          color: Colors.transparent,
          elevation: 0,
          child: TabBar(
            controller: tabController,
            indicatorColor: Colors.transparent,
            indicatorWeight: 1,
            labelColor: TColor.primary,
            labelStyle: const TextStyle(fontSize: 10),
            unselectedLabelColor: TColor.primaryText28,
            unselectedLabelStyle: const TextStyle(fontSize: 10),
            tabs: [
              Tab(
                text: 'Chat',
                icon: Image.asset(
                  selectTab == 0 ? ImagesAssset.home : ImagesAssset.homeUn,
                  width: 20,
                  height: 20,
                ),
              ),
              Tab(
                text: 'News',
                icon: Image.asset(
                  selectTab == 1 ? ImagesAssset.news : ImagesAssset.newsUn,
                  width: 20,
                  height: 20,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
