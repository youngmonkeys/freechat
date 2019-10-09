package vn.team.freechat.repo;

public interface ChatBotQuestionRepo {

	String findQuestionByIndex(int index);
	
	long count();
}
