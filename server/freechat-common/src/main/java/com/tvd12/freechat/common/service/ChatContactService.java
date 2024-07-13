package com.tvd12.freechat.common.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.common.converter.ChatEntityToModelConverter;
import com.tvd12.freechat.common.converter.ChatModelToEntityConverter;
import com.tvd12.freechat.common.entity.ChatContactId;
import com.tvd12.freechat.common.model.ChatContactModel;
import com.tvd12.freechat.common.model.ChatSaveContactModel;
import com.tvd12.freechat.common.repo.ChatContactRepo;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class ChatContactService {

    private final ChatContactRepo contactRepo;
    private final ChatEntityToModelConverter entityToModelConverter;
    private final ChatModelToEntityConverter modelToEntityConverter;

    public void saveContacts(List<ChatSaveContactModel> models) {
        contactRepo.save(
            newArrayList(
                models,
                modelToEntityConverter::toEntity
            )
        );
    }

    public List<ChatContactModel> getContactsByUserIds(
        long userId,
        Collection<Long> userIds
    ) {
        return newArrayList(
            contactRepo
                .findListByIds(
                    newArrayList(
                        userIds,
                        it -> new ChatContactId(it, userId)
                    )
                ),
            entityToModelConverter::toModel
        );
    }

    public Set<Long> getContactedUserIdsByUserIds(
        long userId,
        Collection<Long> userIds
    ) {
        return contactRepo
            .findListByIds(
                newArrayList(
                    userIds,
                    it -> new ChatContactId(it, userId)
                )
            )
            .stream()
            .flatMap(it ->
                Stream.of(
                    it.getId().getUser1stId(),
                    it.getId().getUser2ndId()
                )
            )
            .collect(Collectors.toSet());
    }
}
