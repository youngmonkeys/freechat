package com.tvd12.freechat.app.data;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.freechat.common.entity.ChatUser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EzyObjectBinding(read = false)
public class ChatContactUser {
    private final ChatUser user;

    public String getUsername() {
        return user.getUsername();
    }

    public String getFullName() {
        return user.getFullName();
    }
}
