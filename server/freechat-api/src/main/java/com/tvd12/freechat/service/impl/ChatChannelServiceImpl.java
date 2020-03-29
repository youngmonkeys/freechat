package com.tvd12.freechat.service.impl;

import java.util.List;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.common.service.ChatMaxIdService;
import com.tvd12.freechat.constant.ChatEntities;
import com.tvd12.freechat.data.ChatChannel;
import com.tvd12.freechat.repo.ChatChannelRepo;
import com.tvd12.freechat.service.ChatChannelService;

import lombok.Setter;

@Setter
@EzySingleton("channelService")
public class ChatChannelServiceImpl implements ChatChannelService {

	@EzyAutoBind
	private ChatMaxIdService maxIdService;
	
	@EzyAutoBind
	protected ChatChannelRepo channelRepo;
	
	@Override
	public long newChannelId() {
		long newId = maxIdService.incrementAndGet(ChatEntities.CHAT_CHANNEL);
		return newId;
	}
	
	@Override
	public void saveChannels(List<ChatChannel> channels) {
		channelRepo.save(channels);
	}

}
