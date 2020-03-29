package com.tvd12.freechat.plugin.testing;

import org.testng.annotations.Test;

import com.tvd12.freechat.common.service.impl.ChatUserServiceImpl;

public class ChatUserServiceImplTest extends HazelcastBaseTesting {
	
	@Test
	public void test() throws Exception {
		ChatUserServiceImpl service = new ChatUserServiceImpl();
		service.setHazelcastInstance(HZ_INSTANCE);
		service.setMaxIdService(MAX_ID_SERVICE);
		service.createUser("dungtv", u -> {});
		Thread.sleep(3000);
	}
	
}
