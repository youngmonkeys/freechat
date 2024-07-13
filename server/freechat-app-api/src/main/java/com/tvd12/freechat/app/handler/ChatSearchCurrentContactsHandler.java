package com.tvd12.freechat.app.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.app.converter.ChatAppModelToResponseConverter;
import com.tvd12.freechat.common.model.ChatContactUserModel;
import com.tvd12.freechat.common.service.ChatUserService;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

import static com.tvd12.freechat.app.constant.ChatCommands.SEARCH_CURRENT_CONTACTS;
import static com.tvd12.freechat.app.util.EzyUsers.getDbUserId;
import static com.tvd12.freechat.common.constant.ChatConstants.MAX_SEARCH_CONTACT;

@Setter
@EzyPrototype
@EzyObjectBinding
@EzyRequestListener(SEARCH_CURRENT_CONTACTS)
public class ChatSearchCurrentContactsHandler
    extends ChatClientRequestHandler
    implements EzyDataBinding {

    private long userIdGt;
    private String keyword;
    private int limit;

    @EzyAutoBind
    private ChatUserService userService;

    @EzyAutoBind
    private ChatAppModelToResponseConverter modelToResponseConverter;

    @Override
    protected void preExecute() {
        if (limit > MAX_SEARCH_CONTACT) {
            limit = MAX_SEARCH_CONTACT;
        }
    }

    @Override
    protected void execute() throws EzyBadRequestException {
        long dbUserId = getDbUserId(user);
        List<ChatContactUserModel> contactUsers = userService
            .searchContactedUsers(
                dbUserId,
                userIdGt,
                keyword,
                limit
            );
        response(contactUsers);
    }

    public void response(
        List<ChatContactUserModel> contactUsers
    ) {
        responseFactory.newArrayResponse()
            .command(SEARCH_CURRENT_CONTACTS)
            .session(session)
            .data(
                contactUsers
                    .stream()
                    .map(it ->
                        modelToResponseConverter
                            .toChatChannelUsersResponse(it)
                    )
                    .collect(Collectors.toList())
            )
            .execute();
    }
}

