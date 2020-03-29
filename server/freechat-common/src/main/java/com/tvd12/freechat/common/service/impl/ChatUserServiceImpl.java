package com.tvd12.freechat.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.function.EzyApply;
import com.tvd12.freechat.common.constant.ChatEntities;
import com.tvd12.freechat.common.data.ChatNewUser;
import com.tvd12.freechat.common.data.ChatUser;
import com.tvd12.freechat.common.repo.ChatUserRepo;
import com.tvd12.freechat.common.service.ChatUserService;
import com.tvd12.freechat.common.service.HazelcastMapHasMaxIdService;

import lombok.Setter;

@Setter
@EzySingleton("userService")
public class ChatUserServiceImpl 
		extends HazelcastMapHasMaxIdService<String, ChatUser> 
		implements ChatUserService {

	@EzyAutoBind
	private ChatUserRepo userRepo;
	
	@Override
	public void saveUser(ChatUser user) {
		set(user.getUsername(), user);
		
	}
	
	@Override
	public ChatUser getUser(String username) {
		return get(username);
	}
	
	@Override
	public ChatNewUser createUser(String username, EzyApply<ChatUser> applier) {
		ChatUser user = getUser(username);
		if(user != null) 
			return new ChatNewUser(user, false);
		ChatUser cuser = getUser(username);
		if(cuser != null) 
			return new ChatNewUser(user, false);
		ChatUser nuser = newUser(username);
		applier.apply(nuser);
		map.set(username, nuser);
		return new ChatNewUser(nuser, true);
	}
	
	@Override
	public List<ChatUser> getSearchUsers(String owner, int skip, int limit) {
		ChatUser found = userRepo.findByUsername(owner);
		List<ChatUser> list = new ArrayList<>();
		if(found != null)
			list.add(found);
		return list;
	}
	
	@Override
	public List<ChatUser> getSuggestionUsers(String owner, int skip, int limit) {
		return userRepo.findSuggestionUsers(owner, skip, limit);
	}
	
	private ChatUser newUser(String username) {
		ChatUser user = new ChatUser();
		user.setId(newId(ChatEntities.CHAT_USER));
		user.setUsername(username);
		return user;
	}
	
	@Override
	protected String getMapName() {
		return ChatEntities.CHAT_USER;
	}
}
