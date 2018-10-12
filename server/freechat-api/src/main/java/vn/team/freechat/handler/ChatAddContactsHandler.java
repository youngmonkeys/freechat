package vn.team.freechat.handler;

import static vn.team.freechat.constant.ChatCommands.ADD_CONTACTS;

import java.util.Set;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;

import lombok.Setter;
import vn.team.freechat.repo.ChatContactRepo;

@Setter
@EzyPrototype
@EzyObjectBinding
@EzyClientRequestListener(ADD_CONTACTS)
public class ChatAddContactsHandler 
		extends ChatClientRequestHandler
		implements EzyDataBinding {

	@EzyValue
	private Set<String> target;
	
	@EzyAutoBind
	private ChatContactRepo contactRepo;

	@Override
	protected void execute() throws EzyBadRequestException {
		Set<String> newContacts = contactRepo.addContacts(user.getName(), target);
		response(newContacts);
	}
	
	private void response(Set<String> newContacts) {
		responseFactory.newObjectResponse()
			.command(ADD_CONTACTS)
			.param("new-contacts", newContacts)
			.user(user)
			.execute();
	}
	
}
