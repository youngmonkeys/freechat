package com.tvd12.freechat.common.service.impl;

import java.util.List;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyNext;
import com.tvd12.freechat.common.constant.ChatEntities;
import com.tvd12.freechat.common.entity.ChatUser;
import com.tvd12.freechat.common.repo.ChatUserRepo;
import com.tvd12.freechat.common.service.ChatMaxIdService;
import com.tvd12.freechat.common.service.ChatUserService;

import lombok.Setter;

@Setter
@EzySingleton("userService")
public class ChatUserServiceImpl implements ChatUserService {

	@EzyAutoBind
	private ChatUserRepo userRepo;
	
	@EzyAutoBind
	private ChatMaxIdService maxIdService;
	
	@Override
	public void saveUser(ChatUser user) {
		userRepo.save(user);
	}
	
	@Override
	public ChatUser getUser(String username) {
		return userRepo.findByField("username", username);
	}
	
	@Override
	public ChatUser createUser(String username, String password) {
		ChatUser user = new ChatUser();
		user.setId(maxIdService.incrementAndGet(ChatEntities.CHAT_USER));
		user.setUsername(username);
		user.setPassword(password);
		userRepo.save(user);
		return user;
	}
	
	@Override
	public List<ChatUser> getSearchUsers(String keyword, String owner, int skip, int limit) {
		String regex = ".*" + keyword + ".*";
		return userRepo.findByUsernameRegex(keyword, regex, EzyNext.fromSkipLimit(skip, limit));
	}
	
	@Override
	public List<ChatUser> getSuggestionUsers(String owner, int skip, int limit) {
		return userRepo.findSuggestionUsers(owner, EzyNext.fromSkipLimit(skip, limit));
	}
	
}
