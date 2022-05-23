package com.tvd12.freechat.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.freechat.common.service.ChatMaxIdService;
import com.tvd12.freechat.constant.ChatEntities;
import com.tvd12.freechat.entity.ChatMessage;
import com.tvd12.freechat.service.ChatBotQuestionService;
import com.tvd12.freechat.service.ChatMessageService;
import lombok.Setter;

import static com.tvd12.freechat.constant.ChatCommands.CHAT_SYSTEM_MESSAGE;

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
    private ChatMaxIdService maxIdService;

    @EzyAutoBind
    private ChatMessageService messageService;

    @EzyAutoBind
    private ChatBotQuestionService chatBotQuestionService;

    @Override
    protected void execute() throws EzyBadRequestException {
        String answer = chatBotQuestionService.randomQuestion();
        messageService.save(new ChatMessage(
            maxIdService.incrementAndGet(ChatEntities.CHAT_MESSAGE),
            true, message, 0L, user.getName(), clientMessageId
        ));
        messageService.save(new ChatMessage(
            maxIdService.incrementAndGet(ChatEntities.CHAT_MESSAGE),
            true, answer, 0L, "Bot", ""
        ));
        response(user, answer);
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
