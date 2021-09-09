/**
 * 
 */
package com.tvd12.freechat.plugin;

import java.util.Properties;

import com.tvd12.ezydata.mongodb.loader.EzyMongoClientLoader;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.context.EzyZoneContext;
import com.tvd12.ezyfoxserver.setting.EzyPluginSetting;
import com.tvd12.ezyfoxserver.support.entry.EzySimplePluginEntry;
import com.tvd12.properties.file.util.PropertiesUtil;

/**
 * @author tavandung12
 *
 */
public class ChatPluginEntry extends EzySimplePluginEntry {

	@Override
	protected void preConfig(EzyPluginContext ctx) {
		logger.info("\n=================== FREE CHAT PLUGIN START CONFIG ================\n");
	}
	
	@Override
	protected void postConfig(EzyPluginContext ctx) {
		logger.info("\n=================== FREE CHAT PLUGIN END CONFIG ================\n");
	}
	
	@Override
	protected void setupBeanContext(EzyPluginContext context, EzyBeanContextBuilder builder) {
		EzyPluginSetting setting = context.getPlugin().getSetting();
		builder.addProperties(getConfigFile(setting));
		Properties properties = builder.getProperties();
		Properties mongoProperties = PropertiesUtil.filterPropertiesByKeyPrefix(
				properties, 
				EzyMongoClientLoader.PROPERTY_NAME_PREFIX
		);
		EzyZoneContext zoneContext = context.getParent();
		zoneContext.setProperty("mongoProperties", mongoProperties);
	}
	
	protected String getConfigFile(EzyPluginSetting setting) {
		return setting.getConfigFile();
	}

	@Override
	public void start() throws Exception {
		logger.info("chat plugin: start");
	}
	
	@Override
	protected String[] getScanablePackages() {
		return new String[] {
			"com.tvd12.freechat.common",
			"com.tvd12.freechat.plugin"
		};
	}
}