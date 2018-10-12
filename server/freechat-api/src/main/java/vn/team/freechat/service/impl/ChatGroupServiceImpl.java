package vn.team.freechat.service.impl;

import java.util.List;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

import lombok.Setter;
import vn.team.freechat.common.service.ChatMaxIdService;
import vn.team.freechat.common.service.HazelcastMapHasMaxIdService;
import vn.team.freechat.constant.ChatEntities;
import vn.team.freechat.data.ChatGroup;
import vn.team.freechat.repo.ChatGroupRepo;
import vn.team.freechat.service.ChatGroupService;

@Setter
@EzySingleton("groupService")
public class ChatGroupServiceImpl
		extends HazelcastMapHasMaxIdService<Long, ChatGroup>
		implements ChatGroupService {

	@EzyAutoBind
	private ChatGroupRepo chatGroupRepo;
	
	@EzyAutoBind
	private ChatMaxIdService maxIdService;
	
	@Override
	public void save(ChatGroup group) {
		presave(group);
		set(group.getId(),group);
	}
	
	private void presave(ChatGroup group) {
		long id = newEntityId();
		group.setId(id);
	}
	
	private long newEntityId() {
		return maxIdService.incrementAndGet(ChatEntities.CHAT_GROUP);
	}

	@Override
	public List<ChatGroup> getGroupByGroupName(String groupName) {
		return	chatGroupRepo.findListByField("name", groupName);
	}

	@Override
	public ChatGroup getPrivateGroupById(Long id) {
		
		ChatGroup chatGroup = null;
		chatGroup = chatGroupRepo.findById(id);
		if(chatGroup == null) {
			getLogger().debug("Not Private");
			return null;
		}
		else {
		getLogger().debug("chat group: ",chatGroup.getName());
		getLogger().debug("getGroupById");
		if(chatGroup.getType().equals("TWO")) {
			getLogger().debug("Private Group");
		}
		return chatGroup;
		}
		
	}

	@Override
	protected String getMapName() {
		// TODO Auto-generated method stub
		return ChatEntities.CHAT_GROUP;
	}
	
}
