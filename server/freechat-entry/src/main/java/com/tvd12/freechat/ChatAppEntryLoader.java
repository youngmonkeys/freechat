package com.tvd12.freechat;

import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfoxserver.ext.EzyAbstractAppEntryLoader;
import com.tvd12.ezyfoxserver.ext.EzyAppEntry;

public class ChatAppEntryLoader extends EzyAbstractAppEntryLoader {

    @Override
    public EzyAppEntry load() throws Exception {
        return EzyClasses.newInstance("com.tvd12.freechat.ChatAppEntry");
    }
}
