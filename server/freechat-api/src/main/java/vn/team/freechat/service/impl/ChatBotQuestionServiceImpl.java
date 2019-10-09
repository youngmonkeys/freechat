package vn.team.freechat.service.impl;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

import lombok.Setter;
import vn.team.freechat.repo.ChatBotQuestionRepo;
import vn.team.freechat.service.ChatBotQuestionService;

@Setter
@EzySingleton("chatBotQuestionService")
public class ChatBotQuestionServiceImpl implements ChatBotQuestionService {

	@EzyAutoBind
	protected ChatBotQuestionRepo chatBotQuestionRepo;
	
	@Override
	public String randomQuestion() {
		Random random = ThreadLocalRandom.current();
		long count = chatBotQuestionRepo.count();
		int index = random.nextInt((int)count);
		String question = chatBotQuestionRepo.findQuestionByIndex(index);
		return question;
	}

	
	
}
