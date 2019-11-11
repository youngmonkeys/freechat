package vn.team.freechat.handler;

import static vn.team.freechat.constant.ChatCommands.SUGGEST_CONTACTS;

import java.util.List;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;

import lombok.Setter;
import vn.team.freechat.common.data.ChatUser;
import vn.team.freechat.common.service.ChatUserService;

@Setter
@EzyPrototype
@EzyClientRequestListener(SUGGEST_CONTACTS)
public class ChatSuggestContactsHandler 
		extends ChatClientRequestHandler 
		implements EzyDataBinding {
	
	@EzyAutoBind
	private ChatUserService userService;
	
	@Override
	protected void execute() throws EzyBadRequestException {
		List<ChatUser> users = userService.getSuggestionUsers(user.getName(), 0, 30);
		response(users);
	}
	
	private void response(List<ChatUser> users) {
		responseFactory.newObjectResponse()
			.command(SUGGEST_CONTACTS)
			.param("users", users)
			.session(session)
			.execute();
	}

}
