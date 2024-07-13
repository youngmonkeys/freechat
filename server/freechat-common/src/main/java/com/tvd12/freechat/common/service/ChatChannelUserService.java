package com.tvd12.freechat.common.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.freechat.common.converter.ChatModelToEntityConverter;
import com.tvd12.freechat.common.entity.ChatChannelUser;
import com.tvd12.freechat.common.model.ChatSaveChannelUserModel;
import com.tvd12.freechat.common.repo.ChatChannelUserRepo;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class ChatChannelUserService {

    private final ChatChannelUserRepo channelUserRepo;
    private final ChatModelToEntityConverter modelToEntityConverter;

    public void saveChannelUsers(
        List<ChatSaveChannelUserModel> models
    ) {
        channelUserRepo.save(
            newArrayList(
                models,
                modelToEntityConverter::toEntity
            )
        );
    }

    public List<Long> getUserIdsByChannelId(
        long channelId
    ) {
        return newArrayList(
            channelUserRepo.findListByField(
                "_id.channelId",
                channelId
            ),
            it -> it.getId().getUserId()
        );
    }

    public List<Long> getChannelIdsByUserId(
        long userId,
        int skip,
        int limit
    ) {
        List<ChatChannelUser> list = channelUserRepo.findListByField(
            "_id.userId",
            userId,
            skip,
            limit
        );
        return newArrayList(list, i -> i.getId().getChannelId());
    }

    public Map<Long, List<Long>> getUserIdsMapByChannelIdsAndExclusiveUserId(
        Collection<Long> channelIds,
        long exclusiveUserId
    ) {
        return channelUserRepo.findByChannelIdsAndUserIdNeq(
            channelIds,
            exclusiveUserId
        )
            .stream()
            .filter(it -> it.getId().getUserId() != exclusiveUserId)
            .collect(
                Collectors.groupingBy(
                    it -> it.getId().getChannelId(),
                    Collectors.mapping(
                        it -> it.getId().getUserId(),
                        Collectors.toList()
                    )
                )
            );
    }
}
