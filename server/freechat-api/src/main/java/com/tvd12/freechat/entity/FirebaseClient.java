package com.tvd12.freechat.entity;

import java.io.InputStream;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.stream.EzyAnywayInputStreamLoader;
import com.tvd12.ezyfox.util.EzyLoggable;

import lombok.Setter;

@Setter
@EzySingleton
public class FirebaseClient extends EzyLoggable {

    protected FirebaseMessaging firebaseMessaging;

    public FirebaseClient() {

        try {
            InputStream inputStream = EzyAnywayInputStreamLoader.builder()
                    .build()
                    .load("notify-server-sdk-config.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build();
            FirebaseApp.initializeApp(options);

            firebaseMessaging = FirebaseMessaging.getInstance();
        } catch (Exception e) {
            firebaseMessaging = null;
            logger.info("Loi khi khoi tao firebase: "+ e.getMessage());
        }
    }

    public boolean notify(String token, NotifyMessage message) {
        Message m = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(message.getTitle())
                        .setBody(message.getBody())
                        .setImage(message.getImageURL())
                        .build())
                .putAllData(message.getData())
                .setToken(token)
                .build();
        try {
            firebaseMessaging.send(m);
            return true;
        } catch (Exception e) {
            logger.error("notify to: {} error", token, e);
            return false;
        }
    }


}