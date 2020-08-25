package com.tvd12.freechat.repo.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tvd12.ezydata.morphia.repository.EzyDatastoreRepository;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.data.ChatContact;
import com.tvd12.freechat.data.ChatContactId;
import com.tvd12.freechat.repo.ChatContactRepo;

import dev.morphia.Key;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Query;
import dev.morphia.query.internal.MorphiaCursor;

@EzySingleton("contactRepo")
public class ChatContactRepoImpl
		extends EzyDatastoreRepository<ChatContactId, ChatContact>
		implements ChatContactRepo {

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
		Iterable<Key<ChatContact>> saved = datastore.save(contacts);
		Set<String> answer = new HashSet<>();
		for(Key<ChatContact> key : saved) {
			ChatContactId id = (ChatContactId)key.getId();
			answer.add(actor.equals(id.getUser1st()) ? id.getUser2nd() : id.getUser1st());
		}
		return answer;
	}

	@Override
	public Set<String> getContactNames(String actor, int skip, int limit) {
		Query<ChatContact> query = newQuery();
		query.or(
				query.criteria("id.user1st").equal(actor),
				query.criteria("id.user2nd").equal(actor)
		);
		FindOptions opts = new FindOptions()
				.skip(skip)
				.limit(limit);
		MorphiaCursor<ChatContact> cursor = query.find(opts);
		List<ChatContact> list = cursor.toList();
		Set<String> answer = new HashSet<>();
		for(ChatContact contact : list) {
			String user1st = contact.getId().getUser1st();
			String user2nd = contact.getId().getUser2nd();
			answer.add(user1st.equals(actor) ? user2nd : user1st);
		}
		return answer;
	}
	
	@Override
	public int countContact(String actor) {
		Query<ChatContact> query = newQuery();
		query.or(
				query.criteria("id.user1st").equal(actor),
				query.criteria("id.user2nd").equal(actor)
		);
		long count = query.count();
		return (int)count;
	}

	@Override
	protected Class<ChatContact> getEntityType() {
		return ChatContact.class;
	}

}
