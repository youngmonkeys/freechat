package com.tvd12.freechat.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.setting.EzyAppSetting;
import com.tvd12.freechat.repo.ChatBotQuestionRepo;
import com.tvd12.freechat.repo.impl.ChatBotQuestionRepoFileSystem;

import lombok.Setter;

@Setter
@EzyConfigurationBefore
public class ChatBotQuestionRepoConfig {

	@EzyAutoBind
	protected EzyAppContext appContext;
	
	@EzySingleton
	public ChatBotQuestionRepo chatBotQuestionRepo() throws Exception {
		String fileName = "questions.txt";
		EzyAppSetting setting = appContext.getApp().getSetting();
		String appLocation = setting.getLocation();
		Path path = Paths.get(appLocation, "config", fileName);
		ChatBotQuestionRepo repo = new ChatBotQuestionRepoFileSystem(path);
		return repo;
	}
	
}
