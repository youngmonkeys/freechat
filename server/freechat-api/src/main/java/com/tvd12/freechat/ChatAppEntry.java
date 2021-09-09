package com.tvd12.freechat;

import java.util.Properties;

import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.context.EzyZoneContext;
import com.tvd12.ezyfoxserver.setting.EzyAppSetting;
import com.tvd12.ezyfoxserver.support.entry.EzySimpleAppEntry;

public class ChatAppEntry extends EzySimpleAppEntry {

	@Override
	protected void preConfig(EzyAppContext ctx) {
		logger.info("\n=================== FREE CHAT APP START CONFIG ================\n");
	}
	
	@Override
	protected void postConfig(EzyAppContext ctx) {
		logger.info("\n=================== FREE CHAT APP END CONFIG ================\n");
	}
	
	@Override
	protected void setupBeanContext(EzyAppContext context, EzyBeanContextBuilder builder) {
		EzyAppSetting setting = context.getApp().getSetting();
		EzyZoneContext zoneContext = context.getParent();
		Properties mongoProperties = zoneContext.getProperty("mongoProperties");
		builder.addProperties(mongoProperties);
		builder.addProperties(getConfigFile(setting));
	}
	
	protected String getConfigFile(EzyAppSetting setting) {
		return setting.getConfigFile();
	}
	
	public void start() throws Exception {
		logger.info("start free chat app");
	}
	
	@Override
	protected String[] getScanablePackages() {
		return new String[] {
			"com.tvd12.freechat.common",
			"com.tvd12.freechat.entity",
			"com.tvd12.freechat.data",
			"com.tvd12.freechat.repo",
			"com.tvd12.freechat.controller",
			"com.tvd12.freechat.handler",
			"com.tvd12.freechat.config",
			"com.tvd12.freechat.service"
		};
	}
}
