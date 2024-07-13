package com.tvd12.freechat.app.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.app.constant.ChatErrors;
import com.tvd12.freechat.common.entity.ChatMessageStatus;
import com.tvd12.freechat.common.model.ChatSaveMessageModel;
import com.tvd12.freechat.common.service.ChatChannelService;
import com.tvd12.freechat.common.service.ChatChannelUserService;
import com.tvd12.freechat.common.service.ChatMessageService;
import com.tvd12.freechat.common.service.ChatUserService;
import lombok.Setter;

import java.util.List;

import static com.tvd12.freechat.app.constant.ChatCommands.CHAT_USER_MESSAGE;
import static com.tvd12.freechat.app.util.EzyUsers.getDbUserId;
import static com.tvd12.freechat.common.constant.ChatConstants.MAX_MESSAGE_LENGTH;

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
    private ChatChannelService channelService;

    @EzyAutoBind
    private ChatChannelUserService channelUserService;

    @EzyAutoBind
    private ChatMessageService messageService;

    @EzyAutoBind
    private ChatUserService userService;

    @Override
    protected void execute() throws EzyBadRequestException {
        List<Long> userIds = validateRequest();
        saveMessage();
        response(userIds);
    }

    private void response(List<Long> userIds) {
        responseFactory.newObjectResponse()
            .command(CHAT_USER_MESSAGE)
            .param("from", user.getName())
            .param("message", message)
            .param("channelId", channelId)
            .usernames(userService.getUsernamesByUserId(userIds))
            .execute();
    }

    private void saveMessage() {
        messageService.save(
            ChatSaveMessageModel.builder()
                .message(message)
                .channelId(channelId)
                .senderId(getDbUserId(user))
                .clientMessageId(clientMessageId)
                .status(ChatMessageStatus.SENT)
                .build()
        );
        channelService.saveChannelLastChattedAt(channelId);
    }

    private List<Long> validateRequest() {
        if (message.length() > MAX_MESSAGE_LENGTH) {
            throw new EzyBadRequestException(
                ChatErrors.MESSAGE_TOO_LONG,
                "message too long"
            );
        }
        List<Long> userIds = channelUserService.getUserIdsByChannelId(
            channelId
        );
        if (userIds.isEmpty()) {
            throw new EzyBadRequestException(
                ChatErrors.CHANNEL_NOT_FOUND,
                "channel with id: " + channelId + " not found"
            );
        }
        return userIds;
    }
}
