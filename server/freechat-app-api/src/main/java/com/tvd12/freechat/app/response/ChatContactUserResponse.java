package com.tvd12.freechat.app.response;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.freechat.common.entity.ChatUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@EzyObjectBinding(read = false)
public class ChatContactUserResponse {
    private String username;
    private String fullName;
}
