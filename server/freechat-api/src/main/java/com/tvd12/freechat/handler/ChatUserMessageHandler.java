package com.tvd12.freechat.handler;

import static com.tvd12.freechat.constant.ChatCommands.CHAT_USER_MESSAGE;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.constant.ChatErrors;
import com.tvd12.freechat.data.ChatChannelUsers;
import com.tvd12.freechat.service.ChatChannelUserService;

import lombok.Setter;

@Setter
@EzyPrototype
@EzyObjectBinding
@EzyClientRequestListener(CHAT_USER_MESSAGE)
public class ChatUserMessageHandler 
		extends ChatClientRequestHandler 
		implements EzyDataBinding {

	private String message;
	private long channelId;
	
	@EzyAutoBind
	private ChatChannelUserService channelUserService;
	
	@Override
	protected void execute() throws EzyBadRequestException {
		if(message.length() > 255)
			throw new EzyBadRequestException(ChatErrors.MESSAGE_TOO_LONG, "message too long");
		
		ChatChannelUsers channelUsers = 
				channelUserService.getChannelUsers(channelId, user.getName());
		
		responseFactory.newObjectResponse()
			.command(CHAT_USER_MESSAGE)
			.param("from", user.getName())
			.param("message", message)
			.param("channelId", channelId)
			.usernames(channelUsers.getUsers())
			.execute();
	}
	
}
