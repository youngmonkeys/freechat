package com.tvd12.freechat.common.service.impl;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyNext;
import com.tvd12.freechat.common.constant.ChatEntities;
import com.tvd12.freechat.common.entity.ChatUser;
import com.tvd12.freechat.common.repo.ChatUserRepo;
import com.tvd12.freechat.common.service.ChatMaxIdService;
import com.tvd12.freechat.common.service.ChatUserService;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@EzySingleton("userService")
public class ChatUserServiceImpl implements ChatUserService {

    @EzyAutoBind
    private ChatUserRepo userRepo;

    @EzyAutoBind
    private ChatMaxIdService maxIdService;

    @Override
    public void saveUser(ChatUser user) {
        userRepo.save(user);
    }

    @Override
    public ChatUser getUser(String username) {
        return userRepo.findByField("username", username);
    }

    @Override
    public ChatUser createUser(String username, String password) {
        ChatUser user = new ChatUser();
        user.setId(maxIdService.incrementAndGet(ChatEntities.CHAT_USER));
        user.setUsername(username);
        user.setPassword(password);
        userRepo.save(user);
        return user;
    }

    @Override
    public List<ChatUser> getSearchUsers(
        Set<String> excludeUsers,
        String keyword, int skip, int limit) {
        String regex = ".*" + keyword + ".*";
        return userRepo.findByUsernameRegex(
            excludeUsers, regex, EzyNext.fromSkipLimit(skip, limit));
    }

    @Override
    public List<ChatUser> getSuggestionUsers(Set<String> excludeUsers, int skip, int limit) {
        return userRepo.findSuggestionUsers(excludeUsers, EzyNext.fromSkipLimit(skip, limit));
    }
}
