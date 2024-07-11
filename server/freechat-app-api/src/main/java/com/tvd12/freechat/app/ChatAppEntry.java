package com.tvd12.freechat.app;

import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.context.EzyZoneContext;
import com.tvd12.ezyfoxserver.setting.EzyAppSetting;
import com.tvd12.ezyfoxserver.support.entry.EzySimpleAppEntry;

import java.util.Properties;

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

    @Override
    public void start() {
        logger.info("start free chat app");
    }

    @Override
    protected String[] getScanablePackages() {
        return new String[] {
            "com.tvd12.freechat.app",
            "com.tvd12.freechat.common"
        };
    }
}
