package com.tvd12.freechat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.embedded.EzyEmbeddedServer;
import com.tvd12.ezyfoxserver.ext.EzyAppEntry;
import com.tvd12.ezyfoxserver.ext.EzyPluginEntry;
import com.tvd12.ezyfoxserver.setting.EzyAppSetting;
import com.tvd12.ezyfoxserver.setting.EzyAppSettingBuilder;
import com.tvd12.ezyfoxserver.setting.EzyPluginSetting;
import com.tvd12.ezyfoxserver.setting.EzyPluginSettingBuilder;
import com.tvd12.ezyfoxserver.setting.EzySettingsBuilder;
import com.tvd12.ezyfoxserver.setting.EzySimpleSettings;
import com.tvd12.ezyfoxserver.setting.EzyZoneSettingBuilder;
import com.tvd12.freechat.plugin.ChatPluginEntry;
import com.tvd12.freechat.plugin.ChatPluginEntryLoader;

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
		public EzyPluginEntry load() throws Exception {
			return new ChatPluginEntry() {
				
				@Override
				protected String getConfigFile(EzyPluginSetting setting) {
					return Paths.get(getPluginPath(setting), "config", "config.properties")
							.toString();
				}
				
				private String getPluginPath(EzyPluginSetting setting) {
					Path pluginPath = Paths.get("freechat-plugin");
					if(!Files.exists(pluginPath))
						pluginPath = Paths.get("../freechat-plugin");
					return pluginPath.toString();
				}
			};
		}
	}
	
	public static class DecoratedAppEntryLoader extends ChatAppEntryLoader {
		
		@Override
		public EzyAppEntry load() throws Exception {
			return new ChatAppEntry() {
				
				@Override
				protected String getConfigFile(EzyAppSetting setting) {
					return Paths.get(getAppPath(), "config", "config.properties")
							.toString();
				}
				
				private String getAppPath() {
					Path pluginPath = Paths.get("freechat-entry");
					if(!Files.exists(pluginPath))
						pluginPath = Paths.get("../freechat-entry");
					return pluginPath.toString();
				}
			};
		}
	}
}
