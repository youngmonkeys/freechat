// void _startListening() async {
//   if (!_isListening) {
//     setState(() {
//       _isListening = true;
//     });

//     await _speechToText.listen(
//       onResult: (result) {
//         if (result.recognizedWords.isNotEmpty) {
//           setState(() {
//             controller.text = result.recognizedWords;
//             _isListening = false;
//           });
//           print('Văn bản nhận diện: ${result.recognizedWords}');
//         } else {
//           print('Không có văn bản nhận diện được');
//         }
//       },
//       onError: (error) {
//         print('Lỗi nhận diện: $error');
//         setState(() {
//           _isListening = false;
//         });
//       },
//     );
//   }
// }
