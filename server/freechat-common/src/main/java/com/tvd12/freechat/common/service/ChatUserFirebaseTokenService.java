package com.tvd12.freechat.common.service;

import com.tvd12.freechat.common.entity.ChatUserFirebaseToken;

import java.util.List;
import java.util.Set;

public interface ChatUserFirebaseTokenService {
    void saveUserFirebaseToken(ChatUserFirebaseToken userFirebaseToken);

    ChatUserFirebaseToken getUserFirebaseToken(String username);

    ChatUserFirebaseToken createUserFirebaseToken(String username, String token);

    List<ChatUserFirebaseToken> findChatUserFirebaseTokens(Set<String> username);

}
