package vn.team.freechat.handler;

import static vn.team.freechat.constant.ChatCommands.ADD_CONTACTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;

import lombok.Setter;
import vn.team.freechat.constant.ChatErrors;
import vn.team.freechat.data.ChatChannel;
import vn.team.freechat.data.ChatChannelUser;
import vn.team.freechat.data.ChatChannelUserId;
import vn.team.freechat.data.ChatChannelUsers;
import vn.team.freechat.repo.ChatContactRepo;
import vn.team.freechat.service.ChatChannelService;
import vn.team.freechat.service.ChatChannelUserService;

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
	
	@EzyAutoBind
	private ChatChannelService channelService;
	
	@EzyAutoBind
	private ChatChannelUserService channelUserService;

	@Override
	protected void execute() throws EzyBadRequestException {
		if(target.size() > 30)
			throw new EzyBadRequestException(ChatErrors.TOO_MANY_CONTACTS, "too many contacts");
		Set<String> newContacts = contactRepo.addContacts(user.getName(), target);
		List<ChatChannelUsers> channelUsers = addChannels(newContacts);
		response(channelUsers);
	}
	
	private List<ChatChannelUsers> addChannels(Set<String> newContacts) {
		List<ChatChannel> newChannels = new ArrayList<>();
		List<ChatChannelUser> newChannelUsers = new ArrayList<>();
		List<ChatChannelUsers> answer = new ArrayList<>();
		for(String contact : newContacts) {
			long channelId = channelService.newChannelId();
			ChatChannel channel = new ChatChannel();
			channel.setId(channelId);
			channel.setCreator(user.getName());
			newChannels.add(channel);
			
			ChatChannelUser channelUser1 = new ChatChannelUser();
			channelUser1.setId(new ChatChannelUserId(channelId, user.getName()));
			newChannelUsers.add(channelUser1);
			
			ChatChannelUser channelUser2 = new ChatChannelUser();
			channelUser2.setId(new ChatChannelUserId(channelId, contact));
			newChannelUsers.add(channelUser2);
			
			answer.add(new ChatChannelUsers(channelId, contact));
		}
		
		channelService.saveChannels(newChannels);
		channelUserService.saveChannelUsers(newChannelUsers);
		
		return answer;
	}
	
	private void response(List<ChatChannelUsers> newContacts) {
		responseFactory.newArrayResponse()
			.command(ADD_CONTACTS)
			.data(newContacts)
			.user(user)
			.execute();
	}
	
}
