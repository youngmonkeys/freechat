package com.tvd12.freechat.app.service;

import java.util.Set;

public interface ChatContactService {

    int getContactCount(String actor);

    Set<String> addContacts(String actor, Set<String> target);

    Set<String> getContactNames(String actor, int skip, int limit);
}
