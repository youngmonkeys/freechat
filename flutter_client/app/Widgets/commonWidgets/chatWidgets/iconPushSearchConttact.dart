// ignore_for_file: file_names

import 'package:flutter/material.dart';

import 'search_contacts.dart';

IconButton iconPushSearchContact(BuildContext context) {
  return IconButton(
    onPressed: () {
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => const SearchContacts(),
        ),
      );
    },
    icon: const Icon(
      Icons.search,
      color: Colors.white,
    ),
  );
}
