// ignore_for_file: non_constant_identifier_names

import 'package:get/get.dart';

List connectContacts = [].obs;
RxList<Map<String, dynamic>> connectContacts2 = <Map<String, dynamic>>[].obs;

List contacts = [].obs;
RxList<Map<String, dynamic>> messages = <Map<String, dynamic>>[].obs;
// List messages = [];
List<String> suggestions = [];
bool alert_dialog = false;
