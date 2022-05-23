package com.tvd12.freechat.common.service;

import com.tvd12.freechat.common.entity.ChatUser;

import java.util.List;
import java.util.Set;

public interface ChatUserService {

    void saveUser(ChatUser user);

    ChatUser getUser(String username);

    ChatUser createUser(String username, String password);

    List<ChatUser> getSearchUsers(
        Set<String> excludeUsers, String keyword, int skip, int limit);

    List<ChatUser> getSuggestionUsers(Set<String> excludeUsers, int skip, int limit);
}
