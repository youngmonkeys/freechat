package vn.team.freechat.handler;

import static vn.team.freechat.constant.ChatCommands.CHAT_GET_CONTACTS;

import java.util.Set;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;

import lombok.Setter;
import vn.team.freechat.repo.ChatContactRepo;

@Setter
@EzyPrototype
@EzyObjectBinding(write = false)
@EzyClientRequestListener(command = CHAT_GET_CONTACTS)
public class ChatGetContactsHandler 
		extends ChatClientRequestHandler
		implements EzyDataBinding {
	
	private int skip;
	private int limit;
	
	@EzyAutoBind
	private ChatContactRepo contactRepo;
	
	@Override
	protected void execute() throws EzyBadRequestException {
		Set<String> contacts = contactRepo.getContactNames(user.getName(), skip, limit);
		reponseMessage(contacts);
	}
	
	public void reponseMessage(Set<String> contacts) {
		responseFactory.newObjectResponse()
			.command(CHAT_GET_CONTACTS)
			.session(session)
			.param("contacts", contacts)
			.execute();
	}

}
