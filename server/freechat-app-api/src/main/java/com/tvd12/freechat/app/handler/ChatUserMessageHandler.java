package com.tvd12.freechat.app.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.common.service.ChatMaxIdService;
import com.tvd12.freechat.app.constant.ChatEntities;
import com.tvd12.freechat.app.constant.ChatErrors;
import com.tvd12.freechat.app.data.ChatChannelUsers;
import com.tvd12.freechat.app.entity.ChatMessage;
import com.tvd12.freechat.app.service.ChatChannelUserService;
import com.tvd12.freechat.app.service.ChatMessageService;
import lombok.Setter;

import static com.tvd12.freechat.app.constant.ChatCommands.CHAT_USER_MESSAGE;

@Setter
@EzyPrototype
@EzyObjectBinding
@EzyRequestListener(CHAT_USER_MESSAGE)
public class ChatUserMessageHandler
    extends ChatClientRequestHandler
    implements EzyDataBinding {

    private String message;
    private long channelId;
    private String clientMessageId = "";

    @EzyAutoBind
    private ChatMaxIdService maxIdService;

    @EzyAutoBind
    private ChatMessageService messageService;

    @EzyAutoBind
    private ChatChannelUserService channelUserService;

    @Override
    protected void execute() throws EzyBadRequestException {
        if (message.length() > 255) {
            throw new EzyBadRequestException(ChatErrors.MESSAGE_TOO_LONG, "message too long");
        }

        ChatChannelUsers channelUsers =
            channelUserService.getChannelUsers(channelId, user.getName());
        if (channelUsers == null) {
            throw new EzyBadRequestException(ChatErrors.CHANNEL_NOT_FOUND, "channel with id: " + channelId + " not found");
        }

        messageService.save(new ChatMessage(
            maxIdService.incrementAndGet(ChatEntities.CHAT_MESSAGE),
            true, message, channelId, user.getName(), clientMessageId
        ));

        responseFactory.newObjectResponse()
            .command(CHAT_USER_MESSAGE)
            .param("from", user.getName())
            .param("message", message)
            .param("channelId", channelId)
            .usernames(channelUsers.getUsers())
            .execute();
    }
}
