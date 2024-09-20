// ignore_for_file: constant_identifier_names, unused_field, unnecessary_null_comparison, non_constant_identifier_names, no_leading_underscores_for_local_identifiers, unused_element, unnecessary_brace_in_string_interps, avoid_print

import 'package:ezyfox_server_flutter_client/ezy_client.dart';
import 'package:ezyfox_server_flutter_client/ezy_clients.dart';
import 'package:ezyfox_server_flutter_client/ezy_config.dart';
import 'package:ezyfox_server_flutter_client/ezy_constants.dart';
import 'package:ezyfox_server_flutter_client/ezy_entities.dart';
import 'package:ezyfox_server_flutter_client/ezy_handlers.dart';

import '../globals.dart';

const ZONE_NAME = "freechat";
const APP_NAME = "freechat";

class SocketProxy {
  bool settedUp = false;
  late String username;
  late String password;
  late EzyClient _client;
  late Function(String)? _greetCallback;
  late Function(String)? _secureChatCallback;
  late Function? _disconnectedCallback;
  late Function? _connectionCallback;
  late Function? _connectionFailedCallback;
  late Function? _requestCallback;
  late Function? _loginErrorCallback;
  late Function(String)? _chatBotResponseCallback;
  late Function(List<String>)? _userListCallback;
  late Function(Map)? _connectUserCallback;
  late Function(List<String>)? _suggestionsCallback;
  late Function(String)? _addContactCallback;
  late Function(List<String>)? _connectedUsersCallback;
  static final SocketProxy _INSTANCE = SocketProxy._();

  SocketProxy._();

  static SocketProxy getInstance() {
    return _INSTANCE;
  }

  void addContact(String username) {
    var app = EzyClients.getInstance().getDefaultClient().zone?.appManager.getAppByName(APP_NAME);
    if (app != null) {
      app.send("5", {"username": username});
    }
  }

  void printAllUsers() {
    print("Danh sách tất cả người dùng:");
    for (var user in contacts) {
      print(user);
    }
  }

  void sendMessage(Map<String, dynamic> message) {
    final client = EzyClients.getInstance().getDefaultClient();
    if (client != null && client.zone != null) {
      var app = client.zone!.appManager.getAppByName(APP_NAME);
      if (app != null) {
        app.send("6", message);
      }
    }
  }

  void _setup() {
    EzyConfig config = EzyConfig();
    config.clientName = ZONE_NAME;
    config.enableSSL = false;
    config.ping.maxLostPingCount = 3;
    config.ping.pingPeriod = 1000;
    config.reconnect.maxReconnectCount = 3;
    config.reconnect.reconnectPeriod = 1000;

    EzyClients clients = EzyClients.getInstance();
    _client = clients.newDefaultClient(config);

    _client.setup.addEventHandler(EzyEventType.DISCONNECTION, _DisconnectionHandler(_disconnectedCallback!));
    _client.setup.addEventHandler(EzyEventType.CONNECTION_SUCCESS, _ConnectionHandler(_connectionCallback!, _client));
    _client.setup.addEventHandler(EzyEventType.CONNECTION_FAILURE, _ConnectionFailureHandler(_connectionFailedCallback!, _client));
    _client.setup.addDataHandler(EzyCommand.HANDSHAKE, _HandshakeHandler());
    _client.setup.addDataHandler(EzyCommand.LOGIN, _LoginSuccessHandler());
    _client.setup.addDataHandler(EzyCommand.APP_ACCESS, _AppAccessHandler(_client));
    _client.setup.addDataHandler(EzyCommand.LOGIN_ERROR, _LoginErrorHandler(_loginErrorCallback!));

    var appSetup = _client.setup.setupApp(APP_NAME);

    appSetup.addDataHandler("4", _ChatBotQuestionHandler((question) {
      print('Câu hỏi từ chatbot: $question');
      _chatBotResponseCallback!(question);
    }));

    appSetup.addDataHandler("1", _UsersListHandler((users) {
      print('Danh sách người dùng: $users');


      // // Chuyển đổi danh sách người dùng thành danh sách tên người dùng
      // List<String> usernames = users.map((user) => user['username'] as String).toList();

      // Cập nhật danh sách contacts
      contacts.addAll(users);
      contacts.toSet().toList();
      // In ra danh sách contacts
      print('Updated contacts: $contacts');

      _userListCallback?.call(users);
    }));

    appSetup.addDataHandler("10", _SuggestionsHandler((suggestions) {
      _suggestionsCallback?.call(suggestions);
    }));

    appSetup.addDataHandler("5", _ConnectedUsersHandler((connectedUsers) {
      print('Danh sách người dùng kết nối: $connectedUsers');
      _connectedUsersCallback?.call(connectedUsers);
    }));

    appSetup.addDataHandler("2", _AddContactHandler((message) {
      _addContactCallback?.call(message as String);
    }));
  }

  void fetchSuggestions() {
    var app = EzyClients.getInstance().getDefaultClient().zone?.appManager.getAppByName(APP_NAME);
    if (app != null) {
      app.send("10", {});
    }
  }

  void fetchUsersList() {
    var app = EzyClients.getInstance().getDefaultClient().zone?.appManager.getAppByName(APP_NAME);
    if (app != null) {
      app.send("1", {});
    }
  }

  void fetchConnectedUsers() {
    var app = EzyClients.getInstance().getDefaultClient().zone?.appManager.getAppByName(APP_NAME);
    if (app != null) {
      app.send("5", {});
    } else {
      print('Không thể tìm thấy ứng dụng "$APP_NAME".');
    }
  }

  void connectToUser(String username) {
    var app = EzyClients.getInstance().getDefaultClient().zone?.appManager.getAppByName(APP_NAME);
    if (app != null) {
      app.send("5", {"username": username});
    }
  }
  // Khi nhận dữ liệu từ server, gọi callback tương ứng
  void handleServerResponse(Map<String, dynamic> response) {
    if (response['data'] != null) {
      if (response['data']['users'] != null) {
        // Danh sách người dùng
        List<String> users = (response['data']['users'] as List)
            .map((user) => user['username'] as String)
            .toList();
        _userListCallback?.call(users);
      }
      if (response['data']['suggestions'] != null) {
        // Danh sách gợi ý
        List<String> suggestions = (response['data']['suggestions'] as List)
            .map((suggestion) => suggestion as String)
            .toList();
        _connectionFailedCallback?.call(suggestions);
      }
    }
  }


  void onUserList(Function(List<String>) callback) {
    _userListCallback = callback;
  }

  void onConnectUserResponse(Function(Map) callback) {
    _connectUserCallback = callback;
  }

  void connectToServer(String username, String password) {
    if (!settedUp) {
      settedUp = true;
      _setup();
    }
    this.username = username;
    this.password = password;
    _client.connect("10.0.2.2", 3005);
  }

  void disconnect() {
    _client.disconnect();
  }

  void onGreet(Function(String) callback) {
    _greetCallback = callback;
  }

  void onSecureChat(Function(String) callback) {
    _secureChatCallback = callback;
  }

  void onDisconnected(Function callback) {
    _disconnectedCallback = callback;
  }

  void onConnection(Function callback) {
    _connectionCallback = callback;
  }

  void onConnectionFailed(Function callback) {
    _connectionFailedCallback = callback;
  }

  void onContacts(Function callback) {
    _connectionFailedCallback = callback;
  }

  void onData(Function callback) {
    _requestCallback = callback;
  }

  void onLoginError(Function callback) {
    _loginErrorCallback = callback;
  }

  void sendMessageToChatBot(String message) {
    var app = EzyClients.getInstance().getDefaultClient().zone?.appManager.getAppByName(APP_NAME);
    if (app != null) {
      app.send("4", {"message": message});
    }
  }

  void onChatBotResponse(Function(String) callback) {
    _chatBotResponseCallback = callback;
  }
}

// Xử lý câu hỏi từ chatbot
class _ChatBotQuestionHandler extends EzyAppDataHandler<Map> {
  late Function(String) _callback;

  _ChatBotQuestionHandler(Function(String) callback) {
    _callback = callback;
  }

  @override
  void handle(EzyApp app, Map data) {
    String question = data["message"] ?? "Không có câu hỏi nào từ máy chủ.";
    _callback(question);
  }
}

class _HandshakeHandler extends EzyHandshakeHandler {
  @override
  List getLoginRequest() {
    return [ZONE_NAME, SocketProxy.getInstance().username, SocketProxy.getInstance().password, []];
  }
}

class _LoginSuccessHandler extends EzyLoginSuccessHandler {
  @override
  void handleLoginSuccess(responseData) {
    client.send(EzyCommand.APP_ACCESS, [APP_NAME]);
  }
}

class _AppAccessHandler extends EzyAppAccessHandler {
  late EzyClient _client;

  _AppAccessHandler(EzyClient client) {
    _client = client;
  }

  @override
  void postHandle(EzyApp app, List data) {
    var _data = {"limit": 50, "skip": 0};
    app.send("5", _data);
  }
}

class _DisconnectionHandler extends EzyDisconnectionHandler {
  late Function _callback;

  _DisconnectionHandler(Function callback) {
    _callback = callback;
  }

  @override
  void postHandle(Map event) {
    _callback();
  }
}

class _ConnectionFailureHandler extends EzyConnectionFailureHandler {
  late Function _callback;
  late EzyClient _client;

  _ConnectionFailureHandler(Function callback, EzyClient client) {
    _callback = callback;
    _client = client;
  }

  @override
  void onConnectionFailed(Map event) {
    _callback();
  }
}

class _ConnectionHandler extends EzyConnectionSuccessHandler {
  late Function _callback;

  _ConnectionHandler(Function callback, EzyClient client) {
    _callback = callback;
  }

  @override
  void handle(Map event) {
    sendHandshakeRequest();
    postHandle();
    _callback();
  }
}

// Xử lý lỗi đăng nhập
class _LoginErrorHandler extends EzyLoginErrorHandler {
  late Function _callback;

  _LoginErrorHandler(Function callback) {
    _callback = callback;
  }

  @override
  void handle(List data) {
    client.disconnect();
    _callback();
  }
}

// Xử lý danh sách người dùng
class _UsersListHandler extends EzyAppDataHandler<Map> {
  late Function(List<String>) _callback;

  _UsersListHandler(Function(List<String>) callback) {
    _callback = callback;
  }

  @override
  void handle(EzyApp app, Map data) {
    List<String> users = [];
    print('cac user tu sever: $users');
    for (var user in data["users"]) {
      print('cac username" $user');

      users.add(user["username"].toString());
    }
    _callback(users);
  }
}

// Xử lý kết nối người dùng
class _ConnectUserHandler extends EzyAppDataHandler<Map> {
  late Function(Map) _callback;

  _ConnectUserHandler(Function(Map) callback) {
    _callback = callback;
  }

  @override
  void handle(EzyApp app, Map data) {
    _callback(data);
  }
}

// Xử lý gợi ý
class _SuggestionsHandler extends EzyAppDataHandler<List<String>> {
  late Function(List<String>) _callback;

  _SuggestionsHandler(Function(List<String>) callback) {
    _callback = callback;
  }

  @override
  void handle(EzyApp app, List<String> data) {
    _callback(data);
  }
}

// Xử lý người dùng kết nối
class _ConnectedUsersHandler extends EzyAppDataHandler<List<String>> {
  late Function(List<String>) _callback;

  _ConnectedUsersHandler(Function(List<String>) callback) {
    _callback = callback;
  }

  @override
  void handle(EzyApp app, List<String> data) {
    _callback(data);
  }
}

// Xử lý thêm bạn
class _AddContactHandler extends EzyAppDataHandler<Map> {
  late Function(Map) _callback;

  _AddContactHandler(Function(Map) callback) {
    _callback = callback;
  }

  @override
  void handle(EzyApp app, Map data) {
    _callback(data);
  }
}
