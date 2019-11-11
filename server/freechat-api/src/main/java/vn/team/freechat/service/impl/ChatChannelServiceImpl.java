package vn.team.freechat.service.impl;

import java.util.List;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

import lombok.Setter;
import vn.team.freechat.common.service.ChatMaxIdService;
import vn.team.freechat.constant.ChatEntities;
import vn.team.freechat.data.ChatChannel;
import vn.team.freechat.repo.ChatChannelRepo;
import vn.team.freechat.service.ChatChannelService;

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
