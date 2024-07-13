package com.tvd12.freechat.app.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.freechat.common.entity.ChatMessageStatus;
import com.tvd12.freechat.common.model.ChatSaveMessageModel;
import com.tvd12.freechat.app.service.ChatBotQuestionService;
import com.tvd12.freechat.common.service.ChatMessageService;
import lombok.Setter;

import static com.tvd12.freechat.app.constant.ChatCommands.CHAT_SYSTEM_MESSAGE;
import static com.tvd12.freechat.app.util.EzyUsers.getDbUserId;

@Setter
@EzyPrototype
@EzyRequestListener(CHAT_SYSTEM_MESSAGE)
@EzyObjectBinding(write = false)
public class ChatBotMessageHandler
    extends ChatClientRequestHandler
    implements EzyDataBinding {

    @EzyValue
    private String message;

    @EzyValue
    private String clientMessageId = "";

    @EzyAutoBind
    private ChatMessageService messageService;

    @EzyAutoBind
    private ChatBotQuestionService chatBotQuestionService;

    @Override
    protected void execute() throws EzyBadRequestException {
        String answer = chatBotQuestionService.randomQuestion();
        saveMessages(answer);
        response(user, answer);
    }

    private void saveMessages(String answer) {
        messageService.save(
            ChatSaveMessageModel.builder()
                .message(message)
                .channelId(0L)
                .senderId(getDbUserId(user))
                .clientMessageId(clientMessageId)
                .status(ChatMessageStatus.READ)
                .readAt(System.currentTimeMillis())
                .build()
        );
        messageService.save(
            ChatSaveMessageModel.builder()
                .message(answer)
                .channelId(0L)
                .senderId(0L)
                .clientMessageId("")
                .status(ChatMessageStatus.READ)
                .build()
        );
    }

    private void response(EzyUser user, String answer) {
        responseFactory.newObjectResponse()
            .command(CHAT_SYSTEM_MESSAGE)
            .user(user)
            .param("from", "Bot")
            .param("message", answer)
            .param("channelId", 0)
            .execute();
    }
}
