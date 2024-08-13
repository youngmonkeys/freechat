# freechat
Free Chat is a cross-platform socket chat application, which uses [ezyfox-server](https://github.com/youngmonkeys/ezyfox-server) and it's client SDKs

> Official site: [https://youngmonkeys.org/freechat/]

# Supports

- ReactJS
- Android Java
- Android Kotlin (developing)
- iOS swift (developing)
hello

# Usage

## ReactJS build
 
1. Clone source code
2. Move to `reactjs` folder
3. Run `npm install`
4. Run `npm start`
 
## Android build
 
 Required environment:
 
 ```
 - Android studio : Flamingo | 2022.2.1 Patch 2 May 24, 2023 [download - `https://developer.android.com/studio/archive?hl=en`]
 - Android gradle plugin : 7.2.0
 - Gradle version : 7.3.3
 - Android sdk : 28
 ```
 
1. Clone source code
2. Run ```git submodule update --init --recursive``` to clone submodules
3. Import project to ```Android studio```
4. Build and done

## Flutter build

 Required environment:

 ```
 - Visual Studio Code
 - Flutter : 3.22.2
 - Important : Flutter uses Android studios gradle to generate apk, so all the requirements from ```Android Build``` above, must be installed.
			         Not tested with iOs build yet.
 ```

1. Follow instruction for EzyFox Flutter Client SDK - ```https://youngmonkeys.org/ezyfox-server/guides/ezyfox-flutter-client-sdk``` 
2. Place 3 files (chat.dart, socket_proxy.dart, main.dart) from flutter_client directory to your project
3. File main.dart is register/login page you can ommit and navigate directly to chat.dart passing ```username``` and ```password``` from your auth screen.
4. Build and done

## iOS build

1. Clone source code
2. Run ```git submodule update --init --recursive``` to clone submodules
3. Open project on ```XCode```
4. Build and run

## Server-side build and run

 Required environment:
 ```
 - Java JDK version : 11 [ ```OpenJDK11U-jdk_x64_windows_hotspot_11.0.23_9.msi```]
 ```

1. Install mongodb ([Tutorial](https://docs.mongodb.com/manual/administration/install-community/))
2. Create `freechat` db with collection `freechat` in mongodb ([Tutorial](https://www.mongodb.com/basics/create-database))
3. Create `root` user using mongo shell:
```
	use freechat
	db.createUser(
	   {
	     user:"root",
	     pwd:"123456",
	     roles:[ { role: "readWrite", db: "freechat" }]
	   }
	)
```
> Update `server/freechat-plugin/config/config.properties` file to use another password
4. Clone source code
5. Import ```server``` folder into an IDE (Eclipse, Intellij, Netbean, Visual Studio Code)
7. Server requires ```OpenJDK11U-jdk_x64_windows_hotspot_11.0.23_9.msi``` to run, otherwise it will refuse to work with database (it will discover db but won't read/write)
8. Run file [FreechatStartup](https://github.com/youngmonkeys/freechat/blob/master/server/freechat-startup/src/main/java/com/tvd12/freechat/FreechatStartup.java)
> The server opens a websocket at `ws://localhost:2208/ws`

# References
1. [Use embedded ezyfox-server](https://youngmonkeys.org/use-embedded-server/)
2. [Use ES6 client sdk](https://youngmonkeys.org/ezyfox-es6-client-sdk/)
3. [User to ezydata-mongodb](https://youngmonkeys.org/introduce-to-ezymongo/)

# License

Apache License, Version 2.0
