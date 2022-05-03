package com.tvd12.freechat.repo;

public interface ChatBotQuestionRepo {

    String findQuestionByIndex(int index);

    long count();
}
