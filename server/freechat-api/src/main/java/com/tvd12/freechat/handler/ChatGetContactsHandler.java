package com.tvd12.freechat.handler;

import static com.tvd12.freechat.constant.ChatCommands.CHAT_GET_CONTACTS;

import java.util.List;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.data.ChatChannelUsers;
import com.tvd12.freechat.service.ChatChannelUserService;

import lombok.Setter;

@Setter
@EzyPrototype
@EzyObjectBinding(write = false)
@EzyClientRequestListener(command = CHAT_GET_CONTACTS)
public class ChatGetContactsHandler 
		extends ChatClientRequestHandler
		implements EzyDataBinding {
	
	protected int skip;
	protected int limit;
	
	@EzyAutoBind
	private ChatChannelUserService channelUserService;
	
	@Override
	protected void preExecute() {
		if(limit > 30) 
			limit = 30;
	}
	
	@Override
	protected void execute() throws EzyBadRequestException {
		
		List<ChatChannelUsers> channels = 
				channelUserService.getChannelsOfUser(user.getName(), skip, limit);
		reponseMessage(channels);
	}
	
	public void reponseMessage(List<ChatChannelUsers> contacts) {
		responseFactory.newArrayResponse()
			.command(CHAT_GET_CONTACTS)
			.session(session)
			.data(contacts)
			.execute();
	}

}
