package com.tvd12.freechat.service;

import com.tvd12.freechat.common.entity.ChatUserFirebaseToken;
import com.tvd12.freechat.entity.ChatMessage;
import java.util.Set;

public interface NotificationService {

    void notify(Set<ChatUserFirebaseToken> setUsername, ChatMessage message);

}