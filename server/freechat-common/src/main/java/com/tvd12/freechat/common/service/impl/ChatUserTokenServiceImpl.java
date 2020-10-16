package com.tvd12.freechat.common.service.impl;

import com.tvd12.ezydata.hazelcast.service.EzyBeanSimpleHazelcastMapService;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.common.constant.ChatEntities;
import com.tvd12.freechat.common.data.ChatUserToken;
import com.tvd12.freechat.common.repo.ChatUserTokenRepo;
import com.tvd12.freechat.common.service.ChatUserTokenService;
import lombok.Setter;

@Setter
@EzySingleton("userTokenService")
public class ChatUserTokenServiceImpl
        extends EzyBeanSimpleHazelcastMapService<String, ChatUserToken>
        implements ChatUserTokenService {

    @EzyAutoBind
    private ChatUserTokenRepo repo;

    @Override
    public void save(ChatUserToken token) {
        repo.save(token);
    }

    @Override
    protected String getMapName() {
        return ChatEntities.CHAT_USER_TOKEN;
    }
}
