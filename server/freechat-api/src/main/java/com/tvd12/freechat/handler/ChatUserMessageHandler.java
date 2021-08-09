package com.tvd12.freechat.handler;

import static com.tvd12.freechat.constant.ChatCommands.CHAT_USER_MESSAGE;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.common.entity.ChatUserFirebaseToken;
import com.tvd12.freechat.common.service.ChatMaxIdService;
import com.tvd12.freechat.common.service.ChatUserFirebaseTokenService;
import com.tvd12.freechat.constant.ChatEntities;
import com.tvd12.freechat.constant.ChatErrors;
import com.tvd12.freechat.data.ChatChannelUsers;
import com.tvd12.freechat.entity.ChatMessage;
import com.tvd12.freechat.service.ChatChannelUserService;
import com.tvd12.freechat.service.ChatMessageService;
import com.tvd12.freechat.service.NotificationService;
import lombok.Setter;
import java.util.List;

@Setter
@EzyPrototype
@EzyObjectBinding
@EzyRequestListener(CHAT_USER_MESSAGE)
public class ChatUserMessageHandler 
		extends ChatClientRequestHandler 
		implements EzyDataBinding {

	private String message;
	private long channelId;
	private String clientMessageId = "";
	
	@EzyAutoBind
	private ChatMaxIdService maxIdService;
	
	@EzyAutoBind
	private ChatMessageService messageService;
	
	@EzyAutoBind
	private ChatChannelUserService channelUserService;

	@EzyAutoBind
	private NotificationService notificationService;

	@EzyAutoBind
	private ChatUserFirebaseTokenService chatUserFirebaseTokenService;
	@Override
	protected void execute() throws EzyBadRequestException {
		if(message.length() > 255)
			throw new EzyBadRequestException(ChatErrors.MESSAGE_TOO_LONG, "message too long");
		
		ChatChannelUsers channelUsers = 
				channelUserService.getChannelUsers(channelId, user.getName());
		if(channelUsers == null)
			throw new EzyBadRequestException(ChatErrors.CHANNEL_NOT_FOUND, "channel with id: " + channelId + " not found");

		ChatMessage chatMessage = new ChatMessage(
				maxIdService.incrementAndGet(ChatEntities.CHAT_MESSAGE),
				true, message, channelId, user.getName(), clientMessageId);
		messageService.save(chatMessage);
		
		responseFactory.newObjectResponse()
			.command(CHAT_USER_MESSAGE)
			.param("from", user.getName())
			.param("message", message)
			.param("channelId", channelId)
			.usernames(channelUsers.getUsers())
			.execute();

		List<ChatUserFirebaseToken> chatUserFirebaseTokens =
				chatUserFirebaseTokenService.findChatUserFirebaseTokens(channelUsers.getUsers());
		notificationService.notify(chatUserFirebaseTokens, chatMessage);
	}
	
}
