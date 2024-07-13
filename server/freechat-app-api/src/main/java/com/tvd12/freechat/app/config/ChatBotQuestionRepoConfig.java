package com.tvd12.freechat.app.config;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.setting.EzyAppSetting;
import com.tvd12.freechat.common.repo.ChatBotQuestionFileSystemRepo;
import com.tvd12.freechat.common.repo.ChatBotQuestionRepo;
import lombok.Setter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Setter
@EzyConfigurationBefore
public class ChatBotQuestionRepoConfig {

    @EzyAutoBind
    private EzyAppContext appContext;

    @EzySingleton
    public ChatBotQuestionRepo chatBotQuestionRepo() throws Exception {
        String fileName = "questions.txt";
        EzyAppSetting setting = appContext.getApp().getSetting();
        String appLocation = setting.getLocation();
        Path path = Paths.get(appLocation, "config", fileName);
        if (!Files.exists(path)) {
            path = Paths.get("freechat-app-entry/config/questions.txt");
        }
        if (!Files.exists(path)) {
            path = Paths.get("../freechat-app-entry/config/questions.txt");
        }
        if (!Files.exists(path)) {
            path = Paths.get("config/app/questions.txt");
        }
        return new ChatBotQuestionFileSystemRepo(path);
    }
}
