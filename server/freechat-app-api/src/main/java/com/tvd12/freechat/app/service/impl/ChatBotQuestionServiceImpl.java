package com.tvd12.freechat.app.service.impl;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.app.repo.ChatBotQuestionRepo;
import com.tvd12.freechat.app.service.ChatBotQuestionService;
import lombok.Setter;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Setter
@EzySingleton("chatBotQuestionService")
public class ChatBotQuestionServiceImpl implements ChatBotQuestionService {

    @EzyAutoBind
    protected ChatBotQuestionRepo chatBotQuestionRepo;

    @Override
    public String randomQuestion() {
        Random random = ThreadLocalRandom.current();
        long count = chatBotQuestionRepo.count();
        int index = random.nextInt((int) count);
        return chatBotQuestionRepo.findQuestionByIndex(index);
    }
}
