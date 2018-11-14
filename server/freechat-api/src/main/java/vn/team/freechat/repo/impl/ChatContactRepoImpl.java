package vn.team.freechat.repo.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.morphia.repository.EzyDatastoreRepository;

import vn.team.freechat.data.ChatContact;
import vn.team.freechat.data.ChatContactId;
import vn.team.freechat.repo.ChatContactRepo;
import xyz.morphia.Key;
import xyz.morphia.query.FindOptions;
import xyz.morphia.query.Query;

@EzySingleton("contactRepo")
public class ChatContactRepoImpl
		extends EzyDatastoreRepository<ChatContactId, ChatContact>
		implements ChatContactRepo {

	@Override
	public Set<String> addContacts(String actor, Set<String> target) {
		Set<ChatContact> contacts = new HashSet<>();
		for(String un : target) {
			ChatContactId id = new ChatContactId(actor, un);
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
		List<ChatContact> list = query.asList(opts);
		Set<String> answer = new HashSet<>();
		for(ChatContact contact : list) {
			String user1st = contact.getId().getUser1st();
			String user2nd = contact.getId().getUser2nd();
			answer.add(user1st.equals(actor) ? user2nd : user1st);
		}
		return answer;
	}

	@Override
	protected Class<ChatContact> getEntityType() {
		return ChatContact.class;
	}

}
