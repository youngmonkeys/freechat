// ignore_for_file: constant_identifier_names

import 'package:ezyfox_server_flutter_client/ezy_client.dart';
import 'package:ezyfox_server_flutter_client/ezy_clients.dart';
import 'package:ezyfox_server_flutter_client/ezy_config.dart';
import 'package:ezyfox_server_flutter_client/ezy_constants.dart';
import 'package:ezyfox_server_flutter_client/ezy_entities.dart';
import 'package:ezyfox_server_flutter_client/ezy_handlers.dart';

import 'globals.dart';

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
  late Function(String)? _chatBotResponseCallback; // Callback cho ChatBot

  static final SocketProxy _INSTANCE = SocketProxy._();

  SocketProxy._();

  static SocketProxy getInstance() {
    return _INSTANCE;
  }

  void sendMessage(Map<String, dynamic> message) {
    // Ensure you have a connected client before sending a message
    final client = EzyClients.getInstance().getDefaultClient();
    if (client != null && client.zone != null) {
      var app = client.zone!.appManager.getAppByName("freechat");
      if (app != null) {
        print('Sending message to chatbot: $message');
        app.send("6", message);
      }
    }
  }

  void _setup() {
    EzyConfig config = EzyConfig();
    config.clientName = ZONE_NAME;
    config.enableSSL =
    false; // SSL is not active by default using freechat server
    config.ping.maxLostPingCount = 3;
    config.ping.pingPeriod = 1000;
    config.reconnect.maxReconnectCount = 3;
    config.reconnect.reconnectPeriod = 1000;
    EzyClients clients = EzyClients.getInstance();
    _client = clients.newDefaultClient(config);
    _client.setup.addEventHandler(EzyEventType.DISCONNECTION,
        _DisconnectionHandler(_disconnectedCallback!));
    _client.setup.addEventHandler(EzyEventType.CONNECTION_SUCCESS,
        _ConnectionHandler(_connectionCallback!, _client));
    _client.setup.addEventHandler(EzyEventType.CONNECTION_FAILURE,
        _ConnectionFailureHandler(_connectionFailedCallback!, _client));
    _client.setup.addDataHandler(EzyCommand.HANDSHAKE, _HandshakeHandler());
    _client.setup.addDataHandler(EzyCommand.LOGIN, _LoginSuccessHandler());
    _client.setup
        .addDataHandler(EzyCommand.APP_ACCESS, _AppAccessHandler(_client));
    _client.setup.addDataHandler(
        EzyCommand.APP_REQUEST, _RequestHandler(_requestCallback!, _client));
    _client.setup.addDataHandler(
        EzyCommand.LOGIN_ERROR, _LoginErrorHandler(_loginErrorCallback!));
    var appSetup = _client.setup.setupApp(APP_NAME);

    // Xử lý dữ liệu cho câu hỏi chatbot
    appSetup.addDataHandler("4",
        _ChatBotQuestionHandler((question) {
          _chatBotResponseCallback!(question);
        }));
  }

  void connectToServer(String username, String password) {
    if (!settedUp) {
      settedUp = true;
      _setup();
    }
    this.username = username;
    this.password = password;
    _client.connect("10.0.2.2",
        3005); // Android emulator localhost-10.0.2.2 for ios it may be 127.0.0.1
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
    // Đảm bảo bạn có một ứng dụng và có thể gửi tin nhắn
    var app = EzyClients.getInstance().getDefaultClient().zone?.appManager.getAppByName("freechat");
    if (app != null) {
      app.send("4", {"message": message});
    }
  }
  void onChatBotResponse(Function(String) callback) {
    _chatBotResponseCallback = callback;
  }
}

class _ChatBotQuestionHandler extends EzyAppDataHandler<Map> {
  late Function(String) _callback;

  _ChatBotQuestionHandler(Function(String) callback) {
    _callback = callback;
  }

  @override
  void handle(EzyApp app, Map data) {
    print('Received ChatBot Question Data: $data');
    String question = data["question"] ?? "Không có câu hỏi nào từ máy chủ.";
    _callback(question);
  }
}

class _HandshakeHandler extends EzyHandshakeHandler {
  @override
  List getLoginRequest() {
    var request = [];
    request.add(ZONE_NAME);
    request.add(SocketProxy.getInstance().username);
    request.add(SocketProxy.getInstance().password);
    request.add([]);
    return request;
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
    var _data = {};
    _data["limit"] = 50;
    _data["skip"] = 0;
    app.send("5", _data);
  }
}

class _GreetResponseHandler extends EzyAppDataHandler<Map> {
  late Function(String) _callback;

  _GreetResponseHandler(Function(String) callback) {
    _callback = callback;
  }

  @override
  void handle(EzyApp app, Map data) {
    _callback(data["message"]);
    app.send("secureChat", {"who": "Young Monkey"}, true);
  }
}

class _SecureChatResponseHandler extends EzyAppDataHandler<Map> {
  late Function(String) _callback;

  _SecureChatResponseHandler(Function(String) callback) {
    _callback = callback;
  }

  @override
  void handle(EzyApp app, Map data) {
    _callback(data["secure-message"]);
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
  late EzyClient _client;

  _ConnectionHandler(Function callback, EzyClient client) {
    _callback = callback;
    _client = client;
  }

  @override
  void handle(Map event) {
    sendHandshakeRequest();
    postHandle();
    _callback();
  }
}

class _LoginErrorHandler extends EzyAbstractDataHandler {
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

class _RequestHandler extends EzyAbstractDataHandler {
  late Function _callback;
  late EzyClient _client;

  _RequestHandler(Function callback, EzyClient client) {
    _callback = callback;
    _client = client;
  }

  @override
  handle(List data) {
    print('du lieu tra ve la: $data');
    // Handle requests
    if (data[1][0] == '4') {
      // Get contacts
      messages = messages +
          [
            {'from': data[1][1]['from'], 'message': data[1][1]['message']}
          ];
    } else if (data[1][0] == '5') {
      // Get contacts
      print('Processing get contacts');
      contacts = data[1][1] + contacts;
    } else if (data[1][0] == '2') {
      // Add contact
      print('Processing add contacts');
      contacts = data[1][1] + contacts;
    } else if (data[1][0] == '6') {
      // User message
      print('Processing user message');
      messages = messages +
          [
            {'from': data[1][1]['from'], 'message': data[1][1]['message']}
          ];
      EzyApp? app = _client.getApp(); // Sử dụng getApp để lấy ứng dụng
      app?.send("getChatBotQuestion", {"message": data[1][1]['message']});
    } else if (data[1][0] == '1') {
      // Suggest Contacts
      print('Processing suggest contacts 1');
      suggestions = [];
      for (var element in data[1][1]['users']) {
        suggestions = suggestions + [element['username'].toString()];
      }
    } else if (data[1][0] == '10') {
      // Suggest Contacts
      print('Processing suggest contacts 10');

      suggestions = [];
      for (var element in data[1][1]['users']) {
        suggestions = suggestions + [element['username'].toString()];
      }
    }
    _callback();
  }
}
