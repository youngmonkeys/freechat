package com.tvd12.freechat.app.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.app.response.ChatChannelUsersResponse;
import com.tvd12.freechat.common.model.ChatUserModel;
import com.tvd12.freechat.common.service.ChatChannelUserService;
import com.tvd12.freechat.common.service.ChatUserService;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

import static com.tvd12.freechat.app.constant.ChatCommands.CHAT_GET_CONTACTS;
import static com.tvd12.freechat.app.util.EzyUsers.getDbUserId;
import static com.tvd12.freechat.common.constant.ChatConstants.MAX_SEARCH_CONTACT;

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
    private ChatUserService userService;

    @EzyAutoBind
    private ChatChannelUserService channelUserService;

    @Override
    protected void preExecute() {
        if (limit > MAX_SEARCH_CONTACT) {
            limit = MAX_SEARCH_CONTACT;
        }
    }

    @Override
    protected void execute() throws EzyBadRequestException {
        long dbUserId = getDbUserId(user);
        List<Long> channelIds = channelUserService.getChannelIdsByUserId(
            dbUserId,
            skip,
            limit
        );
        Map<Long, List<Long>> userIdsByChannelId = channelUserService
            .getUserIdsMapByChannelIdsAndExclusiveUserId(
                channelIds,
                dbUserId
            );
        Set<Long> userIds = userIdsByChannelId
            .values()
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        Map<Long, ChatUserModel> userById = userService
            .getUserMapById(userIds);
        responseMessage(
            toChatChannelUsersResponses(
                channelIds,
                userIdsByChannelId,
                userById
            )
        );
    }

    private List<ChatChannelUsersResponse> toChatChannelUsersResponses(
        List<Long> channelIds,
        Map<Long, List<Long>> userIdsByChannelId,
        Map<Long, ChatUserModel> userById
    ) {
        return channelIds
            .stream()
            .map(it -> {
                List<Long> channelUserIds = userIdsByChannelId.getOrDefault(
                    it,
                    Collections.emptyList()
                );
                List<String> channelUsers = channelUserIds
                    .stream()
                    .filter(userById::containsKey)
                    .map(userById::get)
                    .map(ChatUserModel::getUsername)
                    .collect(Collectors.toList());
                return new ChatChannelUsersResponse(
                    it,
                    channelUsers
                );
            })
            .filter(it -> !it.getUsers().isEmpty())
            .collect(Collectors.toList());
    }

    public void responseMessage(List<ChatChannelUsersResponse> contacts) {
        logger.debug("get contracts results: {}", contacts);
        responseFactory.newArrayResponse()
            .command(CHAT_GET_CONTACTS)
            .session(session)
            .data(contacts)
            .execute();
    }
}
