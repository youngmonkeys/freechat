package vn.team.freechat.repo;

import java.util.Set;

public interface ChatContactRepo {

	Set<String> addContacts(String actor, Set<String> target);
	
	Set<String> getContactNames(String actor, int skip, int limit);
	
}
