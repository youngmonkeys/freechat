package com.tvd12.freechat.common.service;

import com.tvd12.freechat.common.entity.ChatUserFirebaseToken;

import java.util.Set;

public interface ChatUserFirebaseTokenService {
    void saveUserFirebaseToken(ChatUserFirebaseToken userFirebaseToken);

    ChatUserFirebaseToken getUserFirebaseToken(String username);

    ChatUserFirebaseToken createUserFirebaseToken(String username, String token);

    Set<ChatUserFirebaseToken> findChatUserFirebaseTokens(Set<String> username);
}
