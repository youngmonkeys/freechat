package com.tvd12.freechat;

import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.embedded.EzyEmbeddedServer;
import com.tvd12.ezyfoxserver.ext.EzyAppEntry;
import com.tvd12.ezyfoxserver.ext.EzyPluginEntry;
import com.tvd12.ezyfoxserver.setting.*;
import com.tvd12.freechat.plugin.ChatPluginEntry;
import com.tvd12.freechat.plugin.ChatPluginEntryLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FreechatStartup {

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
                    return Paths.get(getPluginPath(), "config", "config.properties")
                        .toString();
                }

                private String getPluginPath() {
                    Path pluginPath = Paths.get("freechat-plugin");
                    if (!Files.exists(pluginPath)) {
                        pluginPath = Paths.get("../freechat-plugin");
                    }
                    return pluginPath.toString();
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
                    return Paths.get(getAppPath(), "config", "config.properties")
                        .toString();
                }

                private String getAppPath() {
                    Path pluginPath = Paths.get("freechat-entry");
                    if (!Files.exists(pluginPath)) {
                        pluginPath = Paths.get("../freechat-entry");
                    }
                    return pluginPath.toString();
                }
            };
        }
    }
}
