package com.tvd12.freechat.app.repo;

public interface ChatBotQuestionRepo {

    String findQuestionByIndex(int index);

    long count();
}
