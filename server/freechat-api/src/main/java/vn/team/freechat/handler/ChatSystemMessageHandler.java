package vn.team.freechat.handler;

import static vn.team.freechat.constant.ChatCommands.CHAT_SYSTEM_MESSAGE;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfoxserver.entity.EzyUser;

import lombok.Setter;
import vn.team.freechat.common.service.ChatUserService;
import vn.team.freechat.service.ChatBotQuestionService;

@EzyPrototype
@EzyClientRequestListener(CHAT_SYSTEM_MESSAGE)
@EzyObjectBinding(write = false)
@Setter
public class ChatSystemMessageHandler 
		extends ChatClientRequestHandler 
		implements EzyDataBinding {

	@EzyValue
	private String message;
	
	
	@EzyAutoBind
	private ChatUserService userService;
	
	@EzyAutoBind
	private ChatBotQuestionService chatBotQuestionService;

	@Override
	protected void execute() throws EzyBadRequestException {
		response(user);
	}
	
	private void response(EzyUser user) {
		String question = chatBotQuestionService.randomQuestion();
		responseFactory.newObjectResponse()
			.command(CHAT_SYSTEM_MESSAGE)
			.user(user)
			.param("message", question)
			.execute();
	}

}
