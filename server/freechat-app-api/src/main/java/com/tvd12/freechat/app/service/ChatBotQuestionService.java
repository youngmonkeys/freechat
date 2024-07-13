package com.tvd12.freechat.app.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.common.repo.ChatBotQuestionRepo;
import lombok.AllArgsConstructor;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@EzySingleton
@AllArgsConstructor
public class ChatBotQuestionService {

    private final ChatBotQuestionRepo chatBotQuestionRepo;

    public String randomQuestion() {
        Random random = ThreadLocalRandom.current();
        long count = chatBotQuestionRepo.count();
        int index = random.nextInt((int) count);
        return chatBotQuestionRepo.findQuestionByIndex(index);
    }
}
