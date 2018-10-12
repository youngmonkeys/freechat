package vn.team.freechat.service.impl;

import java.util.Collection;
import java.util.List;

import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.io.EzyLists;

import lombok.Setter;
import vn.team.freechat.common.service.ChatMaxIdService;
import vn.team.freechat.common.service.HazelcastMapHasMaxIdService;
import vn.team.freechat.data.ChatUserGroup;
import vn.team.freechat.data.ChatUserGroup.DataId;
import vn.team.freechat.repo.ChatUserGroupRepo;
import vn.team.freechat.service.ChatUserGroupService;

@Setter
@EzySingleton("userGroupService")
public class ChatUserGroupServiceImpl
		extends HazelcastMapHasMaxIdService<DataId, ChatUserGroup>
		implements ChatUserGroupService {

	@EzyAutoBind
	private ChatUserGroupRepo userChatGroupRepo;
	
	@EzyAutoBind
	private ChatMaxIdService maxIdService;
	

	@Override
	public void save(ChatUserGroup userGroup) {
		set(userGroup.getId(),userGroup);
	}
	
	@Override
	public ChatUserGroup get(DataId key) {
		return get(key);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List<String> getUsersByGroupId(long groupId) {
		EntryObject e = getEntryObject();
		Predicate predicate = e.get("id.groupId").equal(groupId);
		Collection<ChatUserGroup> userGroups = map.values(predicate);
		return EzyLists.newArrayList(userGroups, ug -> ug.getId().getUsername());
	}

	@Override
	public List<ChatUserGroup> getUserGroupByUserName(String groupName) {
		EntryObject e = getEntryObject();
		Predicate<?, ?> predicate = e.get("id.groupName").equal(groupName);
		Collection<ChatUserGroup> userGroups = map.values(predicate);
		return EzyLists.newArrayList(userGroups, ug -> ug);
	}

	@Override
	protected String getMapName() {
		return vn.team.freechat.constant.ChatEntities.CHAT_USER_GROUP;
	}

}
