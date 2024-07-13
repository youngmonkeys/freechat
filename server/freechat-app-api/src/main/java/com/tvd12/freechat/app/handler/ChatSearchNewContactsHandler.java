package com.tvd12.freechat.app.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.app.converter.ChatAppModelToResponseConverter;
import com.tvd12.freechat.common.model.ChatUserModel;
import com.tvd12.freechat.common.service.ChatUserService;
import lombok.Setter;

import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;
import static com.tvd12.freechat.app.constant.ChatCommands.SEARCH_NEW_CONTACTS_USERS;
import static com.tvd12.freechat.app.util.EzyUsers.getDbUserId;
import static com.tvd12.freechat.common.constant.ChatConstants.MAX_SEARCH_CONTACT;
import static com.tvd12.freechat.common.constant.ChatConstants.MAX_SUGGEST_CONTACT;

@Setter
@EzyPrototype
@EzyObjectBinding
@EzyRequestListener(SEARCH_NEW_CONTACTS_USERS)
public class ChatSearchNewContactsHandler
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
        List<ChatUserModel> users = userService
            .searchNewUsers(
                dbUserId,
                userIdGt,
                keyword,
                MAX_SUGGEST_CONTACT
            );
        response(users);
    }

    private void response(List<ChatUserModel> users) {
        responseFactory.newObjectResponse()
            .command(SEARCH_NEW_CONTACTS_USERS)
            .param(
                "users",
                newArrayList(
                    users,
                    modelToResponseConverter::toChatContactUserResponse
                )
            )
            .session(session)
            .execute();
    }
}
