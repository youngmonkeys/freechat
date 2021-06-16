package com.tvd12.freechat.service;

import com.tvd12.freechat.entity.ChatMessage;

public interface NotificationService {

    void send(ChatMessage message);

}
