package com.tvd12.freechat.repo.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tvd12.freechat.repo.ChatBotQuestionRepo;

public class ChatBotQuestionRepoFileSystem implements ChatBotQuestionRepo {

	protected final List<String> questions;
	
	public ChatBotQuestionRepoFileSystem(Path filePath) throws Exception {
		List<String> lines = Files.readAllLines(filePath);
		questions = Collections.unmodifiableList(new ArrayList<>(lines));
	}
	
	@Override
	public String findQuestionByIndex(int index) {
		String question = questions.get(index);
		return question;
	}
	
	@Override
	public long count() {
		int size = questions.size();
		return size;
	}
	
}
