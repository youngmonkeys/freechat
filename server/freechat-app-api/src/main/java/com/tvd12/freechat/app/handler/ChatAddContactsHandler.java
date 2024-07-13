package com.tvd12.freechat.app.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.app.constant.ChatErrors;
import com.tvd12.freechat.app.response.ChatChannelUsersResponse;
import com.tvd12.freechat.common.model.ChatSaveChannelUserModel;
import com.tvd12.freechat.common.model.ChatSaveContactModel;
import com.tvd12.freechat.common.model.ChatUserModel;
import com.tvd12.freechat.common.service.ChatChannelService;
import com.tvd12.freechat.common.service.ChatChannelUserService;
import com.tvd12.freechat.common.service.ChatContactService;
import com.tvd12.freechat.common.service.ChatUserService;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;
import static com.tvd12.freechat.app.constant.ChatCommands.ADD_CONTACTS;
import static com.tvd12.freechat.app.util.EzyUsers.getDbUserId;
import static com.tvd12.freechat.common.constant.ChatConstants.MAX_ADD_CONTACT;

@Setter
@EzyPrototype
@EzyObjectBinding
@EzyRequestListener(ADD_CONTACTS)
public class ChatAddContactsHandler
    extends ChatClientRequestHandler
    implements EzyDataBinding {

    private Set<String> target;

    @EzyAutoBind
    private ChatChannelService channelService;

    @EzyAutoBind
    private ChatChannelUserService channelUserService;

    @EzyAutoBind
    private ChatContactService contactService;

    @EzyAutoBind
    private ChatUserService userService;

    @Override
    protected void execute() throws EzyBadRequestException {
        validateRequest();
        List<ChatChannelUsersResponse> channelUsers = addChannels();
        response(channelUsers);
    }

    private void validateRequest() {
        if (target.size() > MAX_ADD_CONTACT) {
            throw new EzyBadRequestException(
                ChatErrors.TOO_MANY_CONTACTS,
                "too many contacts"
            );
        }
    }

    private List<ChatChannelUsersResponse> addChannels() {
        List<ChatUserModel> users = userService.getUsersByUsernames(
            target
        );
        long dbUserId = getDbUserId(user);
        List<Long> userIds = newArrayList(
            users,
            ChatUserModel::getId
        );
        Map<Long, String> usernameByUserId = users
            .stream()
            .collect(
                Collectors.toMap(
                    ChatUserModel::getId,
                    ChatUserModel::getUsername
                )
            );
        Set<Long> contactedUserId = contactService
            .getContactedUserIdsByUserIds(
                dbUserId,
                userIds
            );
        List<ChatSaveContactModel> saveContactModels =
            new ArrayList<>();
        List<ChatSaveChannelUserModel> saveChannelUserModels =
            new ArrayList<>();
        List<ChatChannelUsersResponse> answer = new ArrayList<>();
        for (long userId : userIds) {
            if (contactedUserId.contains(userId)) {
                continue;
            }
            String username = usernameByUserId.get(
                userId
            );
            long channelId = channelService.createChannel(
                dbUserId,
                user.getName() + ", " + username
            );
            saveContactModels.add(
                ChatSaveContactModel.builder()
                    .user1stId(dbUserId)
                    .user2ndId(userId)
                    .channelId(channelId)
                    .build()
            );
            saveChannelUserModels.add(
                ChatSaveChannelUserModel.builder()
                    .channelId(channelId)
                    .userId(dbUserId)
                    .build()
            );
            saveChannelUserModels.add(
                ChatSaveChannelUserModel.builder()
                    .channelId(channelId)
                    .userId(userId)
                    .build()
            );
            answer.add(
                new ChatChannelUsersResponse(
                    channelId,
                    username
                )
            );
        }
        if (!saveContactModels.isEmpty()) {
            contactService.saveContacts(saveContactModels);
        }
        if (!saveChannelUserModels.isEmpty()) {
            channelUserService.saveChannelUsers(saveChannelUserModels);
        }
        return answer;
    }

    private void response(List<ChatChannelUsersResponse> channelUsers) {
        responseFactory.newArrayResponse()
            .command(ADD_CONTACTS)
            .data(channelUsers)
            .user(user)
            .execute();
        for (ChatChannelUsersResponse chanelUser : channelUsers) {
            responseFactory.newArrayResponse()
                .command(ADD_CONTACTS)
                .data(
                    Collections.singletonList(
                        chanelUser.clone(user.getName())
                    )
                )
                .usernames(chanelUser.getUsers())
                .execute();
        }
    }
}
