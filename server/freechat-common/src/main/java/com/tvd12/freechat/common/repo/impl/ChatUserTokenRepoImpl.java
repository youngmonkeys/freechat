package com.tvd12.freechat.common.repo.impl;

import com.tvd12.ezydata.morphia.repository.EzyDatastoreRepository;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.common.data.ChatUserToken;
import com.tvd12.freechat.common.repo.ChatUserTokenRepo;

@EzySingleton("userTokenRepo")
public class ChatUserTokenRepoImpl
		extends EzyDatastoreRepository<String, ChatUserToken>
		implements ChatUserTokenRepo {

	@Override
	protected Class<ChatUserToken> getEntityType() {
		return ChatUserToken.class;
	}
}
