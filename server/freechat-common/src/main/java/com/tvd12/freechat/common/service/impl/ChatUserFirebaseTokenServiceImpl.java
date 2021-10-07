package com.tvd12.freechat.common.service.impl;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.common.entity.ChatUserFirebaseToken;
import com.tvd12.freechat.common.repo.ChatUserFirebaseTokenRepo;
import com.tvd12.freechat.common.service.ChatUserFirebaseTokenService;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@EzySingleton("chatUserFirebaseTokenService")
public class ChatUserFirebaseTokenServiceImpl implements ChatUserFirebaseTokenService {

    @EzyAutoBind
    private ChatUserFirebaseTokenRepo chatUserFirebaseTokenRepo;

    @Override
    public void saveUserFirebaseToken(ChatUserFirebaseToken userFirebaseToken) {
        chatUserFirebaseTokenRepo.save(userFirebaseToken);
    }

    @Override
    public ChatUserFirebaseToken getUserFirebaseToken(String username) {
        ChatUserFirebaseToken chatUserFirebaseToken = chatUserFirebaseTokenRepo.findByField("username", username);
        return chatUserFirebaseToken;
    }

    @Override
    public ChatUserFirebaseToken createUserFirebaseToken(String username, String token) {
        ChatUserFirebaseToken chatUserFirebaseToken = new ChatUserFirebaseToken();
        chatUserFirebaseToken.setFirebaseToken(token);
        chatUserFirebaseToken.setUsername(username);
        chatUserFirebaseTokenRepo.save(chatUserFirebaseToken);
        return chatUserFirebaseToken;
    }

    @Override
    public List<ChatUserFirebaseToken> findChatUserFirebaseTokens(Set<String> setUsername) {
        return chatUserFirebaseTokenRepo.findByUsernameIn(setUsername);
    }
}
