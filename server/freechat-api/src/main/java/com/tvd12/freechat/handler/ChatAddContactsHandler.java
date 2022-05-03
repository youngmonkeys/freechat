package com.tvd12.freechat.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfox.io.EzyCollections;
import com.tvd12.freechat.constant.ChatErrors;
import com.tvd12.freechat.data.ChatChannelUsers;
import com.tvd12.freechat.entity.ChatChannel;
import com.tvd12.freechat.entity.ChatChannelUser;
import com.tvd12.freechat.entity.ChatChannelUserId;
import com.tvd12.freechat.service.ChatChannelService;
import com.tvd12.freechat.service.ChatChannelUserService;
import com.tvd12.freechat.service.ChatContactService;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tvd12.freechat.constant.ChatCommands.ADD_CONTACTS;

@Setter
@EzyPrototype
@EzyObjectBinding
@EzyRequestListener(ADD_CONTACTS)
public class ChatAddContactsHandler
    extends ChatClientRequestHandler
    implements EzyDataBinding {

    @EzyValue
    private Set<String> target;

    @EzyAutoBind
    private ChatContactService contactService;

    @EzyAutoBind
    private ChatChannelService channelService;

    @EzyAutoBind
    private ChatChannelUserService channelUserService;

    @Override
    protected void execute() throws EzyBadRequestException {
        if (target.size() > 30) {
            throw new EzyBadRequestException(ChatErrors.TOO_MANY_CONTACTS, "too many contacts");
        }
        int currentContactCount = contactService.getContactCount(user.getName());
        int total = currentContactCount + target.size();
        if (total > 30) {
            throw new EzyBadRequestException(ChatErrors.FULL_CONTACTS, "full contacts");
        }

        Set<String> prevContacts = contactService.getContactNames(user.getName(), 0, 30);
        Set<String> targetFilter = filterContacts(target, prevContacts);
        if (EzyCollections.isEmpty(targetFilter)) {
            return;
        }

        Set<String> newContacts = contactService.addContacts(user.getName(), targetFilter);
        List<ChatChannelUsers> channelUsers = addChannels(newContacts);
        response(channelUsers);
    }

    private Set<String> filterContacts(Set<String> target, Set<String> prevContacts) {
        return target.stream().filter(friend -> !prevContacts.contains(friend)).collect(Collectors.toSet());
    }

    private List<ChatChannelUsers> addChannels(Set<String> newContacts) {
        List<ChatChannel> newChannels = new ArrayList<>();
        List<ChatChannelUser> newChannelUsers = new ArrayList<>();
        List<ChatChannelUsers> answer = new ArrayList<>();
        for (String contact : newContacts) {
            long channelId = channelService.newChannelId();
            ChatChannel channel = new ChatChannel();
            channel.setId(channelId);
            channel.setCreator(user.getName());
            newChannels.add(channel);

            ChatChannelUser channelUser1 = new ChatChannelUser();
            channelUser1.setId(new ChatChannelUserId(channelId, user.getName()));
            newChannelUsers.add(channelUser1);

            ChatChannelUser channelUser2 = new ChatChannelUser();
            channelUser2.setId(new ChatChannelUserId(channelId, contact));
            newChannelUsers.add(channelUser2);

            answer.add(new ChatChannelUsers(channelId, contact));
        }
        channelService.saveChannels(newChannels);
        channelUserService.saveChannelUsers(newChannelUsers);

        return answer;
    }

    private void response(List<ChatChannelUsers> channelUsers) {
        responseFactory.newArrayResponse()
            .command(ADD_CONTACTS)
            .data(channelUsers)
            .user(user)
            .execute();
        for (ChatChannelUsers chanelUser : channelUsers) {
            responseFactory.newArrayResponse()
                .command(ADD_CONTACTS)
                .data(Arrays.asList(chanelUser.clone(user.getName())))
                .usernames(chanelUser.getUsers())
                .execute();
        }
    }

}
