package com.tvd12.freechat.service;

import com.tvd12.freechat.common.entity.ChatUserFirebaseToken;
import com.tvd12.freechat.entity.ChatMessage;

import java.util.List;

public interface NotificationService {

    void notify(List<ChatUserFirebaseToken> setUsername, ChatMessage message);

}