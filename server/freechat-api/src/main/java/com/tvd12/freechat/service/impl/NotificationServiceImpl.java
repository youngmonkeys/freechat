package com.tvd12.freechat.service.impl;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.freechat.common.entity.ChatUserFirebaseToken;
import com.tvd12.freechat.entity.ChatMessage;
import com.tvd12.freechat.entity.NotifyMessage;
import com.tvd12.freechat.service.FirebaseClient;
import com.tvd12.freechat.service.NotificationService;
import java.util.List;

@EzySingleton
public class NotificationServiceImpl extends EzyLoggable implements NotificationService {

    @EzyAutoBind
    private FirebaseClient firebaseClient;

    @Override
    public void notify(List<ChatUserFirebaseToken> userTokens, ChatMessage message) {

        NotifyMessage notifyMessage = NotifyMessage.builder()
                .body(message.getMessage()).title("You have a new message: " + message.getSender())
                .imageURL("https://ibb.co/R703kxf").build();

        try {
            userTokens.forEach(userToken -> {
                firebaseClient.notify(userToken.getFirebaseToken(), notifyMessage);
            });
        } catch (Exception e) {
            logger.error("notify to:  error", e);
        }
    }
}
