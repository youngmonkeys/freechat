import 'package:ezyfox_server_flutter_client/ezy_clients.dart';
import 'package:flutter/material.dart';
import '../../../Model/socket_proxy.dart';
import '../../../globals.dart';
import '../../common/color_extention.dart';
import '../../common/images_extention.dart';
import 'container_chatbot.dart';
import 'container_user.dart';
import 'package:speech_to_text/speech_to_text.dart' as stt;

class ChatScreen extends StatefulWidget {
  final String user;
  final int channel;
  final VoidCallback onBackPressed;
  final TextEditingController controller;

  const ChatScreen({
    super.key,
    required this.user,
    required this.channel,
    required this.onBackPressed,
    required this.controller,
  });

  @override
  _ChatScreenState createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen> {
  final stt.SpeechToText _speechToText = stt.SpeechToText();
  bool _isListening = false;
  bool _speechToTextAvailable = false;

  @override
  void initState() {
    super.initState();
    _initSpeechToText();
  }

  void _initSpeechToText() async {
    _speechToTextAvailable = await _speechToText.initialize(
      onError: (error) => print('Lỗi khi khởi tạo SpeechToText: $error'),
    );
    if (_speechToTextAvailable) {
      print('SpeechToText đã được khởi tạo thành công.');
    } else {
      print('SpeechToText không khả dụng.');
    }
  }

  void _startListening() async {
    if (!_isListening && _speechToTextAvailable) {
      setState(() {
        _isListening = true;
      });

      print('Bắt đầu ghi âm...');
      await _speechToText.listen(
        onResult: (result) {
          if (result.recognizedWords.isNotEmpty) {
            setState(() {
              widget.controller.text = result.recognizedWords;
            });
            print('Văn bản nhận diện: ${result.recognizedWords}');
          } else {
            print('Không có văn bản nhận diện được');
          }
        },
      ).catchError((error) {
        print('Lỗi khi ghi âm: $error');
        setState(() {
          _isListening = false;
        });
      });
    } else if (!_speechToTextAvailable) {
      print('SpeechToText chưa sẵn sàng.');
    }
  }

  void _stopListening() {
    _speechToText.stop();
    setState(() {
      _isListening = false;
      widget.controller.clear();
    });
    print('Dừng và xóa bản ghi chuyển giọng nói thành văn bản');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: TColor.bg,
      appBar: AppBar(
        backgroundColor: TColor.bg,
        title: Row(
          children: [
            (widget.user == 'Chat Bot' || widget.user == 'Chat GPT')
                ? Image.asset(
                    ImagesAssset.chatbot,
                    height: 40,
                  )
                : Image.asset(
                    ImagesAssset.user,
                    height: 40,
                  ),
            Text(
              widget.user,
              style: const TextStyle(color: Colors.white),
            ),
          ],
        ),
        leading: Builder(
          builder: (BuildContext context) {
            return IconButton(
              icon: const Icon(
                Icons.arrow_back,
                color: Colors.white,
              ),
              onPressed: widget.onBackPressed,
              tooltip: MaterialLocalizations.of(context).openAppDrawerTooltip,
            );
          },
        ),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Expanded(
              flex: 5,
              child: ListView.builder(
                itemCount: messages.length,
                itemBuilder: (context, index) {
                  return (widget.user == 'Chat Bot')
                      ? ContainerChatbot(index: index, user: widget.user)
                      : ContainerUser(index: index, user: widget.user);
                },
              ),
            ),
            const Divider(
              color: Colors.transparent,
              height: 5,
            ),
            MessageInput(
              controller: widget.controller,
              onSendPressed: () {
                if (widget.controller.text.isNotEmpty) {
                  var message = widget.controller.text.trim();
                  setState(() {
                    messages.add({
                      'from': 'user',
                      'to': widget.user,
                      'message': message,
                    });
                    widget.controller.clear();
                  });

                  if (widget.user == 'Chat Bot') {
                    SocketProxy.getInstance().sendMessageToChatBot(message);
                  } else {
                    var app = EzyClients.getInstance()
                        .getDefaultClient()
                        .zone!
                        .appManager
                        .getAppByName("freechat");
                    var data = {
                      "channelId": widget.channel,
                      "message": message,
                    };
                    app?.send("6", data);
                  }
                }
              },
              startListening: _startListening,
              stopListening: _stopListening,
              isListening: _isListening,
            ),
            const SizedBox(height: 20),
          ],
        ),
      ),
    );
  }
}

class MessageInput extends StatelessWidget {
  final TextEditingController controller;
  final VoidCallback onSendPressed;
  final VoidCallback startListening;
  final VoidCallback stopListening;
  final bool isListening;

  const MessageInput({
    super.key,
    required this.controller,
    required this.onSendPressed,
    required this.startListening,
    required this.stopListening,
    required this.isListening,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Expanded(
          flex: 4,
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Container(
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(5),
                border: Border.all(color: Colors.lightBlueAccent),
                color: Colors.white,
              ),
              child: TextField(
                controller: controller,
                keyboardType: TextInputType.multiline,
                maxLines: 3,
                decoration: const InputDecoration(
                  border: InputBorder.none,
                ),
              ),
            ),
          ),
        ),
        IconButton(
          onPressed: () {
            if (isListening) {
              stopListening();
            } else {
              startListening();
            }
          },
          icon: Icon(
            isListening ? Icons.mic_off : Icons.mic,
            size: 40,
            color: isListening ? Colors.red : Colors.blue,
          ),
        ),
        Expanded(
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Container(
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(5),
                color: Colors.lightBlueAccent,
              ),
              child: IconButton(
                onPressed: onSendPressed,
                icon: const Icon(
                  Icons.send,
                  color: Colors.white,
                ),
              ),
            ),
          ),
        ),
      ],
    );
  }
}
