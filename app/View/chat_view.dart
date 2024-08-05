import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_sound/public/flutter_sound_recorder.dart';
import 'package:path_provider/path_provider.dart';
import 'package:permission_handler/permission_handler.dart';
import '../common_widget/chatWidget/chat_screen.dart';
import '../common_widget/chatWidget/contact_screen.dart';
import '../globals.dart';
import '../main.dart';
import '../Model/socket_proxy.dart';
import 'package:speech_to_text/speech_to_text.dart' as stt;

class Chat extends StatefulWidget {
  const Chat({super.key, this.username, this.password});
  final String? username;
  final String? password;

  @override
  State<Chat> createState() => _ChatState();
}

class _ChatState extends State<Chat> {
  bool screenSelector = true; // true - list screen, false - user screen
  TextEditingController controller = TextEditingController();
  String? user;
  int? channel;
  bool alertDialog = false;
  FlutterSoundRecorder? _recorder;
  bool _isRecording = false;
  String? _filePath;
  stt.SpeechToText _speechToText = stt.SpeechToText();
  bool _isListening = false;

  @override
  void initState() {
    super.initState();
    setup();
    connect();
    navigateToChatScreen(user ?? " ", channel ?? 1);
    navigateToContactScreen();
    _initializeRecorder();
  }

  Future<void> _initializeRecorder() async {
    final micStatus = await Permission.microphone.request();
    if (micStatus != PermissionStatus.granted) {
      throw RecordingPermissionException('Microphone permission not granted');
    }

    _recorder = FlutterSoundRecorder();
    await _recorder
        ?.openRecorder(); // Sử dụng ?. để tránh lỗi nếu _recorder là null

    Directory tempDir = await getTemporaryDirectory();
    _filePath = '${tempDir.path}/flutter_sound.aac';

    // Initialize SpeechToText
    final speechStatus = await _speechToText.initialize();
    if (!speechStatus) {
      throw Exception('Speech recognition not available');
    }
  }

  void handleRecordPressed() async {
    if (_isRecording) {
      await _recorder
          ?.stopRecorder(); // Sử dụng ?. để tránh lỗi nếu _recorder là null
      setState(() {
        _isRecording = false;
      });
      print('Ghi âm kết thúc');
      // Chuyển sang trạng thái nhận dạng giọng nói
      _startListening();
    } else {
      if (_filePath != null) {
        await _recorder?.startRecorder(
          toFile: _filePath,
        );
        setState(() {
          _isRecording = true;
        });
        print('Ghi âm bắt đầu1');
      } else {
        print('Đường dẫn tập tin ghi âm chưa được khởi tạo');
      }
    }
  }

  void _startListening() async {
    print('da vao den chuyen doi ghi am thanh van ban');
    if (!_isListening) {
      print('dao vao den _isListening');
      setState(() {
        _isListening = true;
      });

      await _speechToText.listen(
        onResult: (result) {
          if (result.recognizedWords.isNotEmpty) {
            setState(() {
              controller.text = result.recognizedWords;
              _isListening = false;
            });
            print('Văn bản nhận diện: ${result.recognizedWords}');
          } else {
            print('Không có văn bản nhận diện được');
          }
        },
      );
    }
  }

  @override
  void dispose() {
    _recorder?.closeRecorder(); // Sử dụng ?. để tránh lỗi nếu _recorder là null
    _recorder = null;
    _speechToText.stop();
    super.dispose();
  }

  setup() async {
    SocketProxy socketProxy = SocketProxy.getInstance();

    socketProxy.onDisconnected(() {
      setState(() {});
    });

    socketProxy.onConnectionFailed(() {
      setState(() {});
    });

    socketProxy.onSecureChat((message) {
      if (user == 'Chat Bot') {
        setState(() {
          messages.add({
            'from': 'Chat Bot',
            'to': 'user',
            'message': message,
          });
        });
      }
    });

    socketProxy.onConnection(() {
      setState(() {});
    });

    socketProxy.onData(() {
      setState(() {});
    });

    socketProxy.onLoginError(() {
      setState(() {
        alertDialog = true;
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => const MyApp()),
        );
      });
    });

    socketProxy.onChatBotResponse((question) {
      if (user == 'Chat Bot') {
        setState(() {
          messages.add({
            'from': 'Chat Bot',
            'to': 'user',
            'message': question,
          });
        });
      }
    });
  }

  connect() {
    setState(() {
      SocketProxy.getInstance()
          .connectToServer(widget.username!, widget.password!);
    });
  }

  void navigateToChatScreen(String selectedUser, int selectedChannel) {
    if (selectedUser == null) return; // Kiểm tra nếu user là null

    setState(() {
      screenSelector = false;
      user = selectedUser;
      channel = selectedChannel;
    });
  }

  void navigateToContactScreen() {
    setState(() {
      screenSelector = true;
    });
  }

  @override
  Widget build(BuildContext context) {
    return screenSelector
        ? ContactScreen(
            onUserSelected: navigateToChatScreen,
          )
        : ChatScreen(
            user: user ?? " ",
            channel: channel ?? 0,
            onBackPressed: navigateToContactScreen,
            onRecordPressed: handleRecordPressed,
            controller: controller,
          );
  }
}
