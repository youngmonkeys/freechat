package com.tvd12.freechat.handler;

import static com.tvd12.freechat.constant.ChatCommands.SUGGEST_CONTACTS;

import java.util.List;
import java.util.Set;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfox.io.EzyLists;
import com.tvd12.freechat.common.entity.ChatUser;
import com.tvd12.freechat.common.service.ChatUserService;
import com.tvd12.freechat.data.ChatContactUser;
import com.tvd12.freechat.service.ChatChannelUserService;

import lombok.Setter;

@Setter
@EzyPrototype
@EzyClientRequestListener(SUGGEST_CONTACTS)
public class ChatSuggestContactsHandler 
		extends ChatClientRequestHandler 
		implements EzyDataBinding {
	
	@EzyAutoBind
	private ChatUserService userService;
	
	@EzyAutoBind
	private ChatChannelUserService channelUserService;
	
	@Override
	protected void execute() throws EzyBadRequestException {
		Set<String> excludeUsers = channelUserService.getContactedUsers(user.getName(), 0, 30);
		List<ChatUser> users = userService.getSuggestionUsers(excludeUsers, 0, 30);
		response(users);
	}
	
	private void response(List<ChatUser> users) {
		responseFactory.newObjectResponse()
			.command(SUGGEST_CONTACTS)
			.param("users", EzyLists.newArrayList(users, ChatContactUser::new))
			.session(session)
			.execute();
	}

}
