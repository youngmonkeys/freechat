package com.tvd12.freechat.app.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.app.data.ChatChannelUsers;
import com.tvd12.freechat.app.service.ChatChannelUserService;
import lombok.Setter;

import java.util.List;

import static com.tvd12.freechat.app.constant.ChatCommands.CHAT_GET_CONTACTS;

@Setter
@EzyPrototype
@EzyObjectBinding(write = false)
@EzyRequestListener(command = CHAT_GET_CONTACTS)
public class ChatGetContactsHandler
    extends ChatClientRequestHandler
    implements EzyDataBinding {

    protected int skip;
    protected int limit;

    @EzyAutoBind
    private ChatChannelUserService channelUserService;

    @Override
    protected void preExecute() {
        if (limit > 30) {
            limit = 30;
        }
    }

    @Override
    protected void execute() throws EzyBadRequestException {
        List<ChatChannelUsers> channels = channelUserService.getChannelsOfUser(user.getName(), skip, limit);
        responseMessage(channels);
    }

    public void responseMessage(List<ChatChannelUsers> contacts) {
        logger.debug("get contracts results: {}", contacts);
        responseFactory.newArrayResponse()
            .command(CHAT_GET_CONTACTS)
            .session(session)
            .data(contacts)
            .execute();
    }
}
