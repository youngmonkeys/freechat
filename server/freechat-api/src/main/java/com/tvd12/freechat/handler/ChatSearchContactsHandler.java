package com.tvd12.freechat.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfox.io.EzyCollections;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.freechat.data.ChatChannelUsers;
import com.tvd12.freechat.service.ChatChannelUserService;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tvd12.freechat.constant.ChatCommands.SEARCH_CONTACTS;

@Setter
@EzyPrototype
@EzyObjectBinding
@EzyRequestListener(SEARCH_CONTACTS)
public class ChatSearchContactsHandler
    extends ChatClientRequestHandler
    implements EzyDataBinding {

    protected int skip;
    protected int limit;
    private String keyword;

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
        logger.debug("search contracts user name: {}, keyword: {}, skip: {}, limit: {}", user.getName(), keyword, skip, limit);
        List<ChatChannelUsers> channels = channelUserService.getChannelsOfUser(user.getName(), skip, limit);
        List<ChatChannelUsers> answers = filterChannels(keyword, channels);
        reponseMessage(answers);
    }

    public void reponseMessage(List<ChatChannelUsers> contacts) {
        logger.debug("search contracts results: {}", contacts);
        responseFactory.newArrayResponse()
            .command(SEARCH_CONTACTS)
            .session(session)
            .data(contacts)
            .execute();
    }

    private List<ChatChannelUsers> filterChannels(String keyword, List<ChatChannelUsers> channels) {
        if (EzyStrings.isEmpty(keyword)) {
            return channels;
        }
        List<ChatChannelUsers> answers = new ArrayList<>();
        for (ChatChannelUsers channel : channels) {
            Set<String> users = filterUsersOfChannel(keyword, channel);
            if (EzyCollections.isEmpty(users)) {
                continue;
            }
            ChatChannelUsers answer = new ChatChannelUsers(channel.getChannelId(), users);
            answers.add(answer);
        }
        return answers;
    }

    private Set<String> filterUsersOfChannel(String keyword, ChatChannelUsers channel) {
        Set<String> validUsers = new HashSet<>();
        for (String user : channel.getUsers()) {
            if (user.contains(keyword)) {
                validUsers.add(user);
            }
        }
        return validUsers;
    }
}

