package com.tvd12.freechat.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.io.EzySets;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.freechat.entity.ChatContact;
import com.tvd12.freechat.entity.ChatContactId;
import com.tvd12.freechat.repo.ChatContactRepo;
import com.tvd12.freechat.service.ChatContactService;

import lombok.Setter;

@Setter
@EzySingleton("contactService")
public class ChatContactServiceImpl implements ChatContactService {

	@EzyAutoBind
	private ChatContactRepo contactRepo;
	
	@Override
	public Set<String> addContacts(String actor, Set<String> target) {
		Set<ChatContact> contacts = new HashSet<>();
		for(String friend : target) {
			ChatContactId id = new ChatContactId(actor, friend);
			ChatContact contact = new ChatContact();
			contact.setId(id);
			contact.setActor(actor);
			contacts.add(contact);
		}
		contactRepo.save(contacts);
		return target;
	}
	
	
	@Override
	public int getContactCount(String actor) {
		return contactRepo.countContactByActor(actor);
	}
	
	@Override
	public Set<String> getContactNames(String actor, int skip, int limit) {
		List<ChatContact> contacts = contactRepo.findContactsByActor(
				actor,
				Next.fromSkipLimit(skip, limit)
		);
		return EzySets.newHashSet(contacts, c ->
			c.getId().getUser1st().equals(actor) 
				? c.getId().getUser2nd()
				: c.getId().getUser1st()
		);
	}
}
