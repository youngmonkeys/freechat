# freechat
a socket chat application

# Introduction 

Free Chat is a socket chat application. It uses #ezyfox-server and it's client SDKs

# Documentation

[https://youngmonkeys.org/freechat/](https://youngmonkeys.org/asset/freechat/)

# It supports

- ReactJS
- Android Java
- Android Kotlin (developing)
- iOS swift (developing)
 
 # ReactJS build
 
 1. Clone source code
 2. Move to ```reactjs``` folder
 3. Run ```npm start```
 
 # Android build
 
 Required environment:
 
 ```
 - Android studio: 3.5
 - Gradle: 3.5.0 or 5.4.1
 - Android sdk: 26
 ```
 
 1. Clone source code
 2. Move to ```android/socket``` folder
 3. Clone ```ezyfox-server-android-client``` by commands:
 
 ```bash
git init
git remote add origin https://github.com/youngmonkeys/ezyfox-server-android-client.git
git pull origin master
```

4. Import project to ```Android studio```
5. Build and done

# iOS build

1. Clone source code
2. Move to ```swift/client```
3. Clone ```ezyfox-server-swift-client``` by commands:

```bash
git init
git remove add origin https://github.com/youngmonkeys/ezyfox-server-swift-client.git
git pull origin master
```

4. Move to ```swift/client/socket```
5. Clone ```ezyfox-server-cpp-client``` by commands:

```bash
git init
git remote add origin https://github.com/youngmonkeys/ezyfox-server-cpp-client.git
git pull origin master
``` 

6. Open project on ```XCode```
7. Build and run

# Server-side build and run

1. Clone source code
2. Import source code (```server``` folder) into IDE (Eclipse, Intellij, Netbean)
3. Run file [FreechatStartup](https://github.com/youngmonkeys/freechat/blob/master/server/freechat-startup/src/main/java/com/tvd12/freechat/FreechatStartup.java)

# License

Apache License, Version 2.0