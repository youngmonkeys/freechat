package com.tvd12.freechat.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

import com.tvd12.freechat.test.entity.ChatMessage;
import com.tvd12.properties.file.reader.BaseFileReader;

public class AbstractMessagePerformanceTest {
	
	protected static Properties properties = new BaseFileReader()
			.read("application.yaml");
	
	protected static List<List<ChatMessage>> prepareMessages() {
		int bulkCount = 10;
		int messageCountPerBulk = 1000;
		AtomicLong idGentor = new AtomicLong();
		List<List<ChatMessage>> messLists = new ArrayList<>();
		for(int i = 0 ; i < bulkCount ; ++i) {
			List<ChatMessage> messageList = new ArrayList<>();
			for(int k = 0 ; k < messageCountPerBulk ; ++k) {
				messageList.add(
					new ChatMessage(
						idGentor.incrementAndGet(), 
						true, 
						"Message#" + i, 
						1L,
						"sender",
						"sentClientMessageId"
					)
				);
			}
			messLists.add(messageList);
		}
		return messLists;
	}
	
}
