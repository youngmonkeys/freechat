package com.tvd12.freechat.common.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.freechat.common.constant.ChatEntities;
import com.tvd12.freechat.common.converter.ChatEntityToModelConverter;
import com.tvd12.freechat.common.entity.ChatUser;
import com.tvd12.freechat.common.model.ChatContactModel;
import com.tvd12.freechat.common.model.ChatContactUserModel;
import com.tvd12.freechat.common.model.ChatUserModel;
import com.tvd12.freechat.common.repo.ChatUserRepo;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.last;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;
import static com.tvd12.ezyfox.io.EzyMaps.newHashMap;

@EzySingleton
@AllArgsConstructor
public class ChatUserService {

    private final ChatContactService contactService;
    private final ChatMaxIdService maxIdService;
    private final ChatUserRepo userRepo;
    private final ChatEntityToModelConverter entityToModelConverter;

    public long createUser(String username, String password) {
        long userId = maxIdService.incrementAndGet(
            ChatEntities.CHAT_USER
        );
        ChatUser user = new ChatUser();
        user.setId(userId);
        user.setUsername(username);
        user.setPassword(password);
        userRepo.save(user);
        return userId;
    }

    public void saveNewUserPassword(
        long userId,
        String newHashPassword
    ) {
        ChatUser entity = userRepo.findById(userId);
        entity.setPassword(newHashPassword);
        entity.setUpdatedAt(System.currentTimeMillis());
        userRepo.save(entity);
    }

    public ChatUserModel getUserByUsername(String username) {
        return entityToModelConverter.toModel(
            userRepo.findByField("username", username)
        );
    }

    public List<String> getUsernamesByUserId(
        Collection<Long> userIds
    ) {
        return newArrayList(
            userRepo.findListByIds(userIds),
            ChatUser::getUsername
        );
    }

    public List<ChatUserModel> getUsersByUsernames(
        Collection<String> usernames
    ) {
        return newArrayList(
            userRepo.findByUsernameIn(usernames),
            entityToModelConverter::toModel
        );
    }

    public List<ChatUserModel> getSuggestionUsers(
        long exclusiveUserId,
        long userIdGt,
        int limit
    ) {
        return getNotInContactsUsers(
            exclusiveUserId,
            userIdGt,
            limit,
            nextUserIdGt -> userRepo.findByIdNeAndIdGt(
                exclusiveUserId,
                nextUserIdGt,
                Next.limit(limit)
            )
        );
    }

    public List<ChatUserModel> searchNewUsers(
        long exclusiveUserId,
        long userIdGt,
        String keyword,
        int limit
    ) {
        String regex = ".*" + keyword + ".*";
        return getNotInContactsUsers(
            exclusiveUserId,
            userIdGt,
            limit,
            nextUserIdGt -> userRepo.findByIdNeAndIdGtAndRegex(
                exclusiveUserId,
                nextUserIdGt,
                regex,
                Next.limit(limit)
            )
        );
    }

    public List<ChatContactUserModel> searchContactedUsers(
        long exclusiveUserId,
        long userIdGt,
        String keyword,
        int limit
    ) {
        long nextUserIdGt = userIdGt;
        String regex = ".*" + keyword + ".*";
        List<ChatContactModel> contacts = new ArrayList<>();
        Map<Long, ChatUser> userById = new HashMap<>();
        while (true) {
            List<ChatUser> entities = userRepo.findByIdNeAndIdGtAndRegex(
                exclusiveUserId,
                nextUserIdGt,
                regex,
                Next.limit(limit)
            );
            userById.putAll(newHashMap(entities, ChatUser::getId));
            List<Long> userIds = newArrayList(
                entities,
                ChatUser::getId
            );
            contacts.addAll(
                contactService
                    .getContactsByUserIds(
                        exclusiveUserId,
                        userIds
                    )
            );
            if (contacts.size() >= limit || entities.size() < limit) {
                break;
            }
            nextUserIdGt = last(entities).getId();
        }
        return newArrayList(
            contacts,
            it -> ChatContactUserModel.builder()
                .user(
                    entityToModelConverter.toModel(
                        userById.get(
                            it.getUser1stId() == exclusiveUserId
                                ? it.getUser2ndId()
                                : it.getUser1stId()
                        )
                    )
                )
                .channelId(it.getChannelId())
                .build()
        );
    }

    public List<ChatUserModel> getNotInContactsUsers(
        long exclusiveUserId,
        long userIdGt,
        int limit,
        Function<Long, List<ChatUser>> usersFetcher
    ) {
        long nextUserIdGt = userIdGt;
        List<ChatUserModel> answer = new ArrayList<>();
        while (true) {
            List<ChatUser> entities = usersFetcher.apply(nextUserIdGt);
            List<Long> userIds = newArrayList(
                entities,
                ChatUser::getId
            );
            Set<Long> contactedUserIds = contactService.getContactedUserIdsByUserIds(
                exclusiveUserId,
                userIds
            );
            answer.addAll(
                entities
                    .stream()
                    .filter(it -> !contactedUserIds.contains(it.getId()))
                    .map(entityToModelConverter::toModel)
                    .collect(Collectors.toList())
            );
            if (answer.size() >= limit || entities.size() < limit) {
                break;
            }
            nextUserIdGt = last(entities).getId();
        }
        return answer;
    }

    public Map<Long, ChatUserModel> getUserMapById(
        Collection<Long> userIds
    ) {
        return userRepo.findListByIds(userIds)
            .stream()
            .collect(
                Collectors.toMap(
                    ChatUser::getId,
                    entityToModelConverter::toModel
                )
            );
    }
}
