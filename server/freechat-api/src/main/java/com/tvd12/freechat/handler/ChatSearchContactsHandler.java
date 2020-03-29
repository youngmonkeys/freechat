package com.tvd12.freechat.handler;

import static com.tvd12.freechat.constant.ChatCommands.SEARCH_CONTACTS;

import java.util.List;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.common.data.ChatUser;
import com.tvd12.freechat.common.service.ChatUserService;

import lombok.Setter;

@Setter
@EzyPrototype
@EzyObjectBinding
@EzyClientRequestListener(SEARCH_CONTACTS)
public class ChatSearchContactsHandler 
		extends ChatClientRequestHandler 
		implements EzyDataBinding {
	
	private String keyword;
	
	@EzyAutoBind
	private ChatUserService userService;
	
	@Override
	protected void execute() throws EzyBadRequestException {
		List<ChatUser> users = userService.getSearchUsers(keyword, 0, 30);
		response(users);
	}
	
	private void response(List<ChatUser> users) {
		responseFactory.newObjectResponse()
			.command(SEARCH_CONTACTS)
			.param("users", users)
			.session(session)
			.execute();
	}

}
