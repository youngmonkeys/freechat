package com.tvd12.freechat.common.repo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatBotQuestionFileSystemRepo implements ChatBotQuestionRepo {

    protected final List<String> questions;

    public ChatBotQuestionFileSystemRepo(Path filePath) throws Exception {
        List<String> lines = Files.readAllLines(filePath);
        questions = Collections.unmodifiableList(new ArrayList<>(lines));
    }

    @Override
    public String findQuestionByIndex(int index) {
        return questions.get(index);
    }

    @Override
    public long count() {
        return questions.size();
    }
}
