package com.tvd12.freechat;

import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.embedded.EzyEmbeddedServer;
import com.tvd12.ezyfoxserver.ext.EzyAppEntry;
import com.tvd12.ezyfoxserver.ext.EzyPluginEntry;
import com.tvd12.ezyfoxserver.setting.*;
import com.tvd12.freechat.app.ChatAppEntry;
import com.tvd12.freechat.app.ChatAppEntryLoader;
import com.tvd12.freechat.plugin.ChatPluginEntry;
import com.tvd12.freechat.plugin.ChatPluginEntryLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApplicationStartup {

    public static void main(String[] args) throws Exception {

        EzyPluginSettingBuilder pluginSettingBuilder = new EzyPluginSettingBuilder()
            .name("freechat")
            .addListenEvent(EzyEventType.USER_LOGIN)
            .entryLoader(DecoratedPluginEntryLoader.class);

        EzyAppSettingBuilder appSettingBuilder = new EzyAppSettingBuilder()
            .name("freechat")
            .entryLoader(DecoratedAppEntryLoader.class);

        EzyZoneSettingBuilder zoneSettingBuilder = new EzyZoneSettingBuilder()
            .name("freechat")
            .application(appSettingBuilder.build())
            .plugin(pluginSettingBuilder.build());

        EzySimpleSettings settings = new EzySettingsBuilder()
            .zone(zoneSettingBuilder.build())
            .build();

        EzyEmbeddedServer server = EzyEmbeddedServer.builder()
            .settings(settings)
            .build();
        server.start();

    }

    public static class DecoratedPluginEntryLoader extends ChatPluginEntryLoader {

        @Override
        public EzyPluginEntry load() {
            return new ChatPluginEntry() {
                @Override
                protected String getConfigFile(EzyPluginSetting setting) {
                    Path configFilePath = Paths.get(
                        "freechat-plugin/config/config.properties"
                    );
                    if (!Files.exists(configFilePath)) {
                        configFilePath = Paths.get(
                            "../freechat-plugin/config/config.properties"
                        );
                    }
                    if (!Files.exists(configFilePath)) {
                        configFilePath = Paths.get(
                            "config/plugin/config.properties"
                        );
                    }
                    return configFilePath.toString();
                }
            };
        }
    }

    public static class DecoratedAppEntryLoader extends ChatAppEntryLoader {

        @Override
        public EzyAppEntry load() {
            return new ChatAppEntry() {
                @Override
                protected String getConfigFile(EzyAppSetting setting) {
                    Path configFilePath = Paths.get(
                        "freechat-app-entry/config/config.properties"
                    );
                    if (!Files.exists(configFilePath)) {
                        configFilePath = Paths.get(
                            "../freechat-app-entry/config/config.properties"
                        );
                    }
                    if (!Files.exists(configFilePath)) {
                        configFilePath = Paths.get(
                            "config/app/config.properties"
                        );
                    }
                    return configFilePath.toString();
                }
            };
        }
    }
}
