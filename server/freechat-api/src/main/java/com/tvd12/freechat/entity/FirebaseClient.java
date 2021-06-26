package com.tvd12.freechat.entity;

import java.io.InputStream;
import java.util.Collection;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.stream.EzyAnywayInputStreamLoader;
import com.tvd12.ezyfox.util.EzyLoggable;

import lombok.Setter;

@Setter
@EzySingleton
public class FirebaseClient extends EzyLoggable {

    protected final FirebaseMessaging firebaseMessaging;

    public FirebaseClient() {

        try {
            InputStream inputStream = EzyAnywayInputStreamLoader.builder()
                    .build()
                    .load("message-project-6af76-firebase-adminsdk-pnm7d-55a9461196.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build();
            FirebaseApp.initializeApp(options);

            firebaseMessaging = FirebaseMessaging.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            FirebaseMessaging.getInstance().send(m);
            return true;
        } catch (Exception e) {
            logger.error("notify to: {} error", token, e);
            return false;
        }
    }

    public int notify(Collection<String> tokens, NotifyMessage message) {
        MulticastMessage m = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(message.getTitle())
                        .setBody(message.getBody())
                        .setImage(message.getImageURL())
                        .build())
                .putAllData(message.getData())
                .addAllTokens(tokens)
                .build();
        try {
            BatchResponse response = firebaseMessaging.sendMulticast(m);
            return response.getSuccessCount();
        } catch (FirebaseMessagingException e) {
            logger.error("notify to: {} clients error", tokens.size(), e);
            return 0;
        }
    }

}