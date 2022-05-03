package com.tvd12.freechat.plugin;

import com.tvd12.ezyfoxserver.ext.EzyAbstractPluginEntryLoader;
import com.tvd12.ezyfoxserver.ext.EzyPluginEntry;

public class ChatPluginEntryLoader extends EzyAbstractPluginEntryLoader {

    @Override
    public EzyPluginEntry load() throws Exception {
        return new ChatPluginEntry();
    }

}
