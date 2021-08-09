# ezyfox-server-android-client <img src="https://github.com/youngmonkeys/ezyfox-server/blob/master/logo.png" width="48" height="48" />
android client for ezyfox server

# Synopsis

android client for ezyfox server

# Documentation
[https://youngmonkeys.org/ezyfox-android-client-sdk/](https://youngmonkeys.org/ezyfox-android-client-sdk/)

# Code Example

**1. Create a TCP Client**

```kotlin
val clients = EzyClients.getInstance()
val client = clients.newClient(config)
```

**2. Setup the client**

```kotlin
val setup = client.setup()
setup.addEventHandler(EzyEventType.CONNECTION_SUCCESS, ExConnectionSuccessHandler())
setup.addEventHandler(EzyEventType.CONNECTION_FAILURE, EzyConnectionFailureHandler())
setup.addEventHandler(EzyEventType.DISCONNECTION, ExDisconnectionHandler())
setup.addDataHandler(EzyCommand.HANDSHAKE, ExHandshakeHandler())
setup.addDataHandler(EzyCommand.LOGIN, ExLoginSuccessHandler())
```

**3. Setup an application**

```kotlin
val appSetup = setup.setupApp(APP_NAME)
appSetup.addDataHandler(Commands.SUGGEST_CONTACTS, SuggestContactsResponseHandler())
appSetup.addDataHandler(Commands.SEARCH_CONTACTS, SearchContactsResponseHandler())
appSetup.addDataHandler(Commands.ADD_CONTACTS, AddContactsResponseHandler())
```
# Used By
1. [freechat](https://youngmonkeys.org/asset/freechat/)