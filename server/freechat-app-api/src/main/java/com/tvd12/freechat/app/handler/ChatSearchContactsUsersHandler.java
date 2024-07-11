package com.tvd12.freechat.app.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfox.io.EzyLists;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.freechat.common.entity.ChatUser;
import com.tvd12.freechat.common.service.ChatUserService;
import com.tvd12.freechat.app.data.ChatContactUser;
import com.tvd12.freechat.app.service.ChatChannelUserService;
import lombok.Setter;

import java.util.List;
import java.util.Set;

import static com.tvd12.freechat.app.constant.ChatCommands.SEARCH_CONTACTS_USERS;

@Setter
@EzyPrototype
@EzyObjectBinding
@EzyRequestListener(SEARCH_CONTACTS_USERS)
public class ChatSearchContactsUsersHandler
    extends ChatClientRequestHandler
    implements EzyDataBinding {

    protected int skip;
    protected int limit;
    private String keyword;

    @EzyAutoBind
    private ChatUserService userService;

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
        logger.debug("searched user by username: {}, skip: {}, limit: {}", keyword, skip, limit);
        if (EzyStrings.isEmpty(keyword)) {
            response(Lists.newArrayList());
            return;
        }
        Set<String> excludeUsers = channelUserService.getContactedUsers(user.getName(), 0, 30);
        List<ChatUser> users = userService.getSearchUsers(excludeUsers, keyword, skip, limit);
        response(users);
    }

    private void response(List<ChatUser> users) {
        responseFactory.newObjectResponse()
            .command(SEARCH_CONTACTS_USERS)
            .param("users", EzyLists.newArrayList(users, ChatContactUser::new))
            .session(session)
            .execute();
    }
}
