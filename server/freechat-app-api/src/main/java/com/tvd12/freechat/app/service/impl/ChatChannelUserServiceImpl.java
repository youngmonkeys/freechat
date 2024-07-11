package com.tvd12.freechat.app.service.impl;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.io.EzyLists;
import com.tvd12.ezyfox.util.EzyHashMapSet;
import com.tvd12.ezyfox.util.EzyMapSet;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.freechat.app.data.ChatChannelUsers;
import com.tvd12.freechat.app.entity.ChatChannelUser;
import com.tvd12.freechat.app.entity.ChatChannelUserId;
import com.tvd12.freechat.app.repo.ChatChannelUserRepo;
import com.tvd12.freechat.app.service.ChatChannelUserService;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Setter
@EzySingleton("channelUserService")
public class ChatChannelUserServiceImpl implements ChatChannelUserService {

    @EzyAutoBind
    protected ChatChannelUserRepo channelUserRepo;

    @Override
    public void saveChannelUsers(List<ChatChannelUser> channelUsers) {
        channelUserRepo.save(channelUsers);
    }

    @Override
    public ChatChannelUser getChannelUser(long channelId, String user) {
        return channelUserRepo.findById(new ChatChannelUserId(channelId, user));
    }

    @Override
    public ChatChannelUsers getChannelUsers(long channelId, String user) {
        List<ChatChannelUser> list = channelUserRepo.findByChannelId(channelId, user);
        Map<Long, Set<String>> map = mapChannelUsers(list);
        return new ChatChannelUsers(channelId, map.get(channelId));
    }

    @Override
    public List<Long> getChannelsIdOfUser(String user, int skip, int limit) {
        Next next = Next.fromSkipLimit(skip, limit);
        List<ChatChannelUser> list = channelUserRepo.findByUser(user, next);
        return EzyLists.newArrayList(list, i -> i.getId().getChannelId());
    }

    @Override
    public List<ChatChannelUsers> getChannelsOfUser(String user, int skip, int limit) {
        List<Long> channelIds = getChannelsIdOfUser(user, skip, limit);
        return getChannelsOfUser(channelIds, user);
    }

    @Override
    public List<ChatChannelUsers> getChannelsOfUser(List<Long> channelIds, String user) {
        List<ChatChannelUser> list = channelUserRepo.findByChannelIds(channelIds, user);
        Map<Long, Set<String>> map = mapChannelUsers(list);
        List<ChatChannelUsers> answer = new ArrayList<>();
        for (Long channelId : map.keySet()) {
            answer.add(new ChatChannelUsers(channelId, map.get(channelId)));
        }
        return answer;
    }

    @Override
    public Set<String> getContactedUsers(String user, int skip, int limit) {
        List<ChatChannelUsers> channels = getChannelsOfUser(user, 0, 30);
        Set<String> contactedUsers = new HashSet<>();
        contactedUsers.add(user);
        contactedUsers.addAll(
            channels.stream()
                .map(ChatChannelUsers::getUsers)
                .flatMap(Set::stream)
                .collect(Collectors.toSet())
        );
        return contactedUsers;
    }

    protected Map<Long, Set<String>> mapChannelUsers(List<ChatChannelUser> list) {
        EzyMapSet<Long, String> map = new EzyHashMapSet<>();
        for (ChatChannelUser item : list) {
            map.addItem(item.getId().getChannelId(), item.getId().getUser());
        }
        return map;
    }
}
