package vn.team.freechat.handler;

import static vn.team.freechat.constant.ChatCommands.CHAT_USER_MESSAGE;

import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;

import lombok.Setter;

@Setter
@EzyPrototype
@EzyObjectBinding
@EzyClientRequestListener(CHAT_USER_MESSAGE)
public class ChatUserMessageHandler 
		extends ChatClientRequestHandler 
		implements EzyDataBinding {

	private String message;
	private String to;
	
	@Override
	protected void execute() throws EzyBadRequestException {
		responseFactory.newObjectResponse()
			.command(CHAT_USER_MESSAGE)
			.param("from", user.getName())
			.param("message", message)
			.username(to)
			.execute();
	}
	
}
